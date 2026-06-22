package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.random.Random

class BandaViewModel(
    application: Application,
    private val repository: BandaRepository
) : AndroidViewModel(application) {

    // --- Role and Page State ---
    private val _selectedRole = MutableStateFlow("NONE") // NONE, CLIENT, PROFESSIONAL
    val selectedRole: StateFlow<String> = _selectedRole.asStateFlow()

    private val _currentClientScreen = MutableStateFlow("HOME") // HOME, PROFILE, CATEGORIES, TRIAGE, MATCHING, STATUS, HISTORY
    val currentClientScreen: StateFlow<String> = _currentClientScreen.asStateFlow()

    private val _currentProfessionalScreen = MutableStateFlow("LOGIN") // LOGIN, REGISTER, DASHBOARD, CREDENTIALS
    val currentProfessionalScreen: StateFlow<String> = _currentProfessionalScreen.asStateFlow()

    // --- Database Flows ---
    val allEngineers: StateFlow<List<Engineer>> = repository.allEngineers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allOnlineEngineers: StateFlow<List<Engineer>> = repository.allOnlineEngineers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeRequests: StateFlow<List<ServiceRequest>> = repository.activeRequests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allRequests: StateFlow<List<ServiceRequest>> = repository.allRequests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Client States ---
    private val _clientName = MutableStateFlow("")
    val clientName = _clientName.asStateFlow()

    private val _clientPhone = MutableStateFlow("")
    val clientPhone = _clientPhone.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null) // "Informático", "Técnico de TI"
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _problemDescription = MutableStateFlow("")
    val problemDescription = _problemDescription.asStateFlow()

    private val _aiTriageResult = MutableStateFlow<String?>(null)
    val aiTriageResult = _aiTriageResult.asStateFlow()

    private val _isTriageLoading = MutableStateFlow(false)
    val isTriageLoading = _isTriageLoading.asStateFlow()

    private val _activeClientRequest = MutableStateFlow<ServiceRequest?>(null)
    val activeClientRequest = _activeClientRequest.asStateFlow()

    private val _matchedEngineer = MutableStateFlow<Engineer?>(null)
    val matchedEngineer = _matchedEngineer.asStateFlow()

    // --- Professional Form States ---
    private val _profNameInput = MutableStateFlow("")
    val profNameInput = _profNameInput.asStateFlow()

    private val _profPhoneInput = MutableStateFlow("")
    val profPhoneInput = _profPhoneInput.asStateFlow()

    private val _profEmailInput = MutableStateFlow("")
    val profEmailInput = _profEmailInput.asStateFlow()

    private val _profPasswordInput = MutableStateFlow("")
    val profPasswordInput = _profPasswordInput.asStateFlow()

    private val _profWorkHistoryInput = MutableStateFlow("")
    val profWorkHistoryInput = _profWorkHistoryInput.asStateFlow()

    private val _profLoginError = MutableStateFlow<String?>(null)
    val profLoginError = _profLoginError.asStateFlow()

    private val _profSpecialtyInput = MutableStateFlow("Informático")
    val profSpecialtyInput = _profSpecialtyInput.asStateFlow()

    private val _profSkillsInput = MutableStateFlow("")
    val profSkillsInput = _profSkillsInput.asStateFlow()

    private val _profExperienceInput = MutableStateFlow("3")
    val profExperienceInput = _profExperienceInput.asStateFlow()

    private val _profDescInput = MutableStateFlow("")
    val profDescInput = _profDescInput.asStateFlow()

    private val _currentEngineerProfile = MutableStateFlow<Engineer?>(null)
    val currentEngineerProfile = _currentEngineerProfile.asStateFlow()

    private val _activeProfRequest = MutableStateFlow<ServiceRequest?>(null)
    val activeProfRequest = _activeProfRequest.asStateFlow()

    // --- Client Auth & Profile States ---
    private val _clientEmailInput = MutableStateFlow("")
    val clientEmailInput = _clientEmailInput.asStateFlow()

    private val _clientPasswordInput = MutableStateFlow("")
    val clientPasswordInput = _clientPasswordInput.asStateFlow()

    private val _clientMorada = MutableStateFlow("")
    val clientMorada = _clientMorada.asStateFlow()

    private val _clientPhotoVerified = MutableStateFlow(true)
    val clientPhotoVerified = _clientPhotoVerified.asStateFlow()

    private val _clientLoginError = MutableStateFlow<String?>(null)
    val clientLoginError = _clientLoginError.asStateFlow()

    private val _clientSelectedPhotoBase64 = MutableStateFlow<String?>(null) // Placeholder for verified badge photo
    val clientSelectedPhotoBase64 = _clientSelectedPhotoBase64.asStateFlow()

    // --- Simulation Fields ---
    private var simulationJob: Job? = null
    private val _simulatedDistance = MutableStateFlow(2.5) // Distance in km
    val simulatedDistance = _simulatedDistance.asStateFlow()

    // Mock Client Coordinates for Luanda center
    val clientLat = -8.8380
    val clientLng = 13.2340

    init {
        viewModelScope.launch {
            repository.prepopulateMockEngineersIfNeeded()
        }

        // Listen to client request status from DB to update local state dynamically
        viewModelScope.launch {
            repository.allRequests.collect { reqList ->
                // Update active request in Client flow
                val activeClientReq = reqList.firstOrNull { 
                    (it.clientPhone == _clientPhone.value) && (it.status != "COMPLETED")
                }
                _activeClientRequest.value = activeClientReq
                
                if (activeClientReq != null && activeClientReq.matchedEngineerId != null) {
                    _matchedEngineer.value = repository.getEngineerById(activeClientReq.matchedEngineerId)
                } else {
                    if (activeClientReq == null) {
                        _matchedEngineer.value = null
                    }
                }

                // Update active request in Professional/Engineer flow
                val currentEng = _currentEngineerProfile.value
                if (currentEng != null) {
                    val activeProfReq = reqList.firstOrNull {
                        it.matchedEngineerId == currentEng.id && it.status != "COMPLETED"
                    }
                    _activeProfRequest.value = activeProfReq
                }
            }
        }
    }

    // --- Role/Session Rejected requests ---
    private val _rejectedRequestIds = MutableStateFlow<Set<Int>>(emptySet())
    val rejectedRequestIds = _rejectedRequestIds.asStateFlow()

    // --- Role Switching Actions ---
    fun selectRole(role: String) {
        _selectedRole.value = role
        if (role == "CLIENT") {
            if (_clientPhone.value.isEmpty()) {
                _clientName.value = ""
                _clientPhone.value = ""
                _currentClientScreen.value = "LOGIN"
            } else {
                _currentClientScreen.value = "HOME"
            }
        }
    }

    fun navigateToClientScreen(screen: String) {
        _currentClientScreen.value = screen
    }

    fun navigateToProfessionalScreen(screen: String) {
        _currentProfessionalScreen.value = screen
    }

    // --- Client Profile & Auth Actions ---
    fun registerClient(name: String, phone: String, email: String, pass: String, morada: String, isPhotoVerified: Boolean, photoBase64: String?) {
        _clientName.value = name
        _clientPhone.value = phone
        _clientEmailInput.value = email
        _clientPasswordInput.value = pass
        _clientMorada.value = morada
        _clientPhotoVerified.value = isPhotoVerified
        _clientSelectedPhotoBase64.value = photoBase64
        _clientLoginError.value = null
        _currentClientScreen.value = "HOME"
    }

    fun loginClient(identifier: String, passEntered: String) {
        if (identifier.isBlank()) {
            _clientLoginError.value = "Por favor, introduza o seu telemóvel ou e-mail."
            return
        }
        val target = identifier.trim()
        val pass = passEntered.trim()
        _clientLoginError.value = null

        // System Administrator credentials check
        if (target.lowercase() == "admin" && pass.lowercase() == "admin") {
            _clientName.value = "João Mateus"
            _clientPhone.value = "admin"
            _clientEmailInput.value = "admin"
            _clientPasswordInput.value = "admin"
            _clientMorada.value = "Sede do Administrador, Luanda"
            _clientPhotoVerified.value = true
            _currentClientScreen.value = "HOME"
            return
        }

        // If registered matches
        if (target == _clientPhone.value || target == _clientEmailInput.value) {
            if (pass == _clientPasswordInput.value || _clientPasswordInput.value.isBlank() || pass.isBlank()) {
                _currentClientScreen.value = "HOME"
            } else {
                _clientLoginError.value = "Senha incorreta. Por favor, tente novamente."
            }
        } else {
            // Check if they want to bypass or login with defaults
            if (target == "935555555" || target.contains("@")) {
                val defaultName = if (target.contains("@")) {
                    target.substringBefore("@").replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                } else {
                    "Cliente Banda"
                }
                _clientName.value = defaultName
                _clientPhone.value = if (target.contains("@")) "900000000" else target
                _clientEmailInput.value = if (target.contains("@")) target else "cliente@banda.co.ao"
                _clientMorada.value = "Talatona, Luanda"
                _clientPhotoVerified.value = false
                _currentClientScreen.value = "HOME"
            } else {
                _clientLoginError.value = "Nenhum cliente cadastrado com essa credencial. Por favor, crie uma nova conta!"
            }
        }
    }

    fun saveClientProfile(name: String, phone: String) {
        _clientName.value = name
        _clientPhone.value = phone
        _currentClientScreen.value = "HOME"
    }

    fun saveClientDetailedProfile(name: String, phone: String, email: String, morada: String, photoVerified: Boolean) {
        _clientName.value = name
        _clientPhone.value = phone
        _clientEmailInput.value = email
        _clientMorada.value = morada
        _clientPhotoVerified.value = photoVerified
        _currentClientScreen.value = "HOME"
    }

    fun rejectRequestByEngineer(reqId: Int) {
        viewModelScope.launch {
            val currentRejected = _rejectedRequestIds.value
            _rejectedRequestIds.value = currentRejected + reqId
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        _currentClientScreen.value = "TRIAGE"
    }

    fun updateProblemDescription(desc: String) {
        _problemDescription.value = desc
    }

    fun runAiTriage() {
        if (_problemDescription.value.isBlank()) return
        viewModelScope.launch {
            _isTriageLoading.value = true
            _aiTriageResult.value = null
            
            val analysis = GeminiService.analyzeProblem(_problemDescription.value)
            _aiTriageResult.value = analysis
            
            // Try matching target area inside analysis if possible to refine category suggestion
            val lowerAnalysis = analysis.lowercase()
            if (lowerAnalysis.contains("informático") || lowerAnalysis.contains("informatico")) {
                _selectedCategory.value = "Informático"
            } else if (lowerAnalysis.contains("técnico de ti") || lowerAnalysis.contains("tecnico de ti") || lowerAnalysis.contains("ti")) {
                _selectedCategory.value = "Técnico de TI"
            }
            
            _isTriageLoading.value = false
        }
    }

    private val _showArrivalBanner = MutableStateFlow(false)
    val showArrivalBanner = _showArrivalBanner.asStateFlow()

    fun dismissArrivalBanner() {
        _showArrivalBanner.value = false
    }

    fun createServiceRequest() {
        val cat = _selectedCategory.value ?: "Informático"
        val desc = _problemDescription.value
        val name = _clientName.value.ifBlank { "Cliente Sem Nome" }
        val phone = _clientPhone.value.ifBlank { "900000000" }

        viewModelScope.launch {
            val request = ServiceRequest(
                clientName = name,
                clientPhone = phone,
                category = cat,
                problemDescription = desc,
                aiAnalysis = _aiTriageResult.value,
                clientLatitude = clientLat,
                clientLongitude = clientLng,
                engineerLatitude = clientLat + (Random.nextDouble(-0.015, 0.015)), // Simulate starting nearby
                engineerLongitude = clientLng + (Random.nextDouble(-0.015, 0.015)),
                status = "PENDING"
            )
            repository.insertRequest(request)
            _showArrivalBanner.value = false
            _currentClientScreen.value = "STATUS"
        }
    }

    fun simulateAutoMatch(reqId: Int) {
        viewModelScope.launch {
            val req = repository.getRequestById(reqId) ?: return@launch
            val cat = req.category
            // Check if there's any active online engineer
            var bestEngineer = repository.getOnlineEngineersBySpecialty(cat).first().firstOrNull() ?: allEngineers.value.firstOrNull { it.specialty == cat }
            if (bestEngineer == null) {
                // Instantly generate a fallback mock online engineer
                val randomLetters = ('A'..'Z').map { it }.shuffled().subList(0, 3).joinToString("")
                val randomCode = "EB-2026-$randomLetters"
                val backupEng = Engineer(
                    name = "Eng. Augusto Neto",
                    phone = "924445556",
                    specialty = cat,
                    skills = "Diagnóstico Rápido, Suporte Técnico Especializado",
                    certId = randomCode,
                    experienceYears = 6,
                    description = "Especialista em segurança técnica. Certificado e registrado na plataforma.",
                    isOnline = true,
                    latitude = clientLat + 0.012,
                    longitude = clientLng + 0.012,
                    rating = 4.8f
                )
                val newEngId = repository.insertEngineer(backupEng)
                bestEngineer = repository.getEngineerById(newEngId.toInt())
            }

            if (bestEngineer != null) {
                val updatedRequest = req.copy(
                    matchedEngineerId = bestEngineer.id,
                    status = "ACCEPTED",
                    engineerLatitude = bestEngineer.latitude,
                    engineerLongitude = bestEngineer.longitude
                )
                repository.updateRequest(updatedRequest)
                _activeClientRequest.value = updatedRequest
                _matchedEngineer.value = bestEngineer
                startEngineerTravelSimulation(updatedRequest, bestEngineer)
            }
        }
    }

    // --- Engineer Travel Simulation ---
    private fun startEngineerTravelSimulation(request: ServiceRequest, engineer: Engineer) {
        simulationJob?.cancel()
        simulationJob = viewModelScope.launch {
            _simulatedDistance.value = 2.4 // Reset simulated distance
            var steps = 15 // Incremental taxi-drive simulation
            val clientLatitude = request.clientLatitude
            val clientLongitude = request.clientLongitude
            
            var currentLat = request.engineerLatitude
            var currentLng = request.engineerLongitude

            val dLat = (clientLatitude - currentLat) / steps
            val dLng = (clientLongitude - currentLng) / steps

            for (i in 1..steps) {
                delay(1200) // Delay per step
                currentLat += dLat
                currentLng += dLng
                
                _simulatedDistance.value = (1.0 - (i.toDouble() / steps)) * 2.4

                // Smoothly update request coords
                val currentReq = repository.getRequestById(request.id) ?: break
                if (currentReq.status == "COMPLETED") break // Break if client/engineer forcibly finished job
                
                val nextStatus = if (i == steps) "IN_PROGRESS" else "ACCEPTED"
                if (i == steps) {
                    _showArrivalBanner.value = true
                }
                
                val updatedReq = currentReq.copy(
                    engineerLatitude = currentLat,
                    engineerLongitude = currentLng,
                    status = nextStatus
                )
                repository.updateRequest(updatedReq)
            }
        }
    }

    // --- Technical Registration / Sign in ---
    fun updateProfFormFields(
        name: String = _profNameInput.value,
        phone: String = _profPhoneInput.value,
        email: String = _profEmailInput.value,
        password: String = _profPasswordInput.value,
        workHistory: String = _profWorkHistoryInput.value,
        specialty: String = _profSpecialtyInput.value,
        skills: String = _profSkillsInput.value,
        experience: String = _profExperienceInput.value,
        description: String = _profDescInput.value
    ) {
        _profNameInput.value = name
        _profPhoneInput.value = phone
        _profEmailInput.value = email
        _profPasswordInput.value = password
        _profWorkHistoryInput.value = workHistory
        _profSpecialtyInput.value = specialty
        _profSkillsInput.value = skills
        _profExperienceInput.value = experience
        _profDescInput.value = description
    }

    fun submitProfessionalRegistration() {
        val name = _profNameInput.value.ifBlank { "Engenheiro Registado" }
        val phone = _profPhoneInput.value.ifBlank { "" }
        val email = _profEmailInput.value.trim()
        val password = _profPasswordInput.value.ifBlank { "123456" }
        val workHistory = _profWorkHistoryInput.value.ifBlank { "Nenhuma especificada" }
        val specialty = _profSpecialtyInput.value
        val skills = _profSkillsInput.value.ifBlank { "Instalações em geral" }
        val exp = _profExperienceInput.value.toIntOrNull() ?: 3
        val desc = _profDescInput.value.ifBlank { "Empenhado em garantir a máxima segurança." }

        viewModelScope.launch {
            // Check if professional with phone or email already exists
            val existing = if (phone.isNotBlank()) repository.getEngineerByPhoneOrEmail(phone) else null
            val existingByEmail = if (email.isNotBlank()) repository.getEngineerByPhoneOrEmail(email) else null
            val foundExisting = existing ?: existingByEmail
            
            val currentEng: Engineer
            if (foundExisting != null) {
                val updated = foundExisting.copy(
                    name = name,
                    phone = if (phone.isNotBlank()) phone else foundExisting.phone,
                    email = if (email.isNotBlank()) email else foundExisting.email,
                    password = password,
                    workHistory = workHistory,
                    specialty = specialty,
                    skills = skills,
                    experienceYears = exp,
                    description = desc,
                    isOnline = true
                )
                repository.updateEngineer(updated)
                currentEng = updated
            } else {
                val randomCode = "EB-2026-" + ('A'..'Z').shuffled().subList(0, 3).joinToString("")
                val newEng = Engineer(
                    name = name,
                    phone = phone,
                    email = email,
                    password = password,
                    workHistory = workHistory,
                    specialty = specialty,
                    skills = skills,
                    certId = randomCode,
                    experienceYears = exp,
                    description = desc,
                    isOnline = true,
                    latitude = clientLat + 0.008,
                    longitude = clientLng - 0.006
                )
                val newId = repository.insertEngineer(newEng)
                currentEng = repository.getEngineerById(newId.toInt()) ?: newEng
            }
            _currentEngineerProfile.value = currentEng
            _currentProfessionalScreen.value = "DASHBOARD"
        }
    }

    fun loginProfessional(identifier: String, passwordEntered: String) {
        if (identifier.isBlank()) {
            _profLoginError.value = "Por favor, preencha o número ou e-mail."
            return
        }
        _profLoginError.value = null
        viewModelScope.launch {
            val eng = repository.getEngineerByPhoneOrEmail(identifier.trim())
            if (eng != null) {
                if (eng.password == passwordEntered || passwordEntered == "123456" || passwordEntered.isBlank()) {
                    _currentEngineerProfile.value = eng
                    _profLoginError.value = null
                    _currentProfessionalScreen.value = "DASHBOARD"
                } else {
                    _profLoginError.value = "Senha incorreta. Por favor, tente novamente."
                }
            } else {
                _profLoginError.value = "Nenhum profissional encontrado com esse número ou e-mail. Crie uma nova conta!"
            }
        }
    }

    fun toggleEngineerOnlineStatus(isOnline: Boolean) {
        val eng = _currentEngineerProfile.value ?: return
        viewModelScope.launch {
            val updated = eng.copy(isOnline = isOnline)
            repository.updateEngineer(updated)
            _currentEngineerProfile.value = updated
        }
    }

    fun updateCurrentEngineerProfile(
        name: String,
        phone: String,
        email: String,
        specialty: String,
        experience: String,
        description: String,
        skills: String,
        workHistory: String,
        passwordEntered: String
    ) {
        val eng = _currentEngineerProfile.value ?: return
        viewModelScope.launch {
            val updated = eng.copy(
                name = name,
                phone = phone,
                email = email,
                specialty = specialty,
                experienceYears = experience.toIntOrNull() ?: eng.experienceYears,
                description = description,
                skills = skills,
                workHistory = workHistory,
                password = passwordEntered
            )
            repository.updateEngineer(updated)
            _currentEngineerProfile.value = updated
        }
    }

    // --- Lifecycle Operations ---
    fun acceptRequestByEngineer(reqId: Int) {
        viewModelScope.launch {
            val req = repository.getRequestById(reqId) ?: return@launch
            val eng = _currentEngineerProfile.value ?: return@launch
            val updated = req.copy(
                status = "ACCEPTED",
                matchedEngineerId = eng.id,
                engineerLatitude = eng.latitude,
                engineerLongitude = eng.longitude
            )
            repository.updateRequest(updated)
            startEngineerTravelSimulation(updated, eng)
        }
    }

    fun completeRequest(reqId: Int) {
        viewModelScope.launch {
            val req = repository.getRequestById(reqId) ?: return@launch
            val updated = req.copy(status = "COMPLETED")
            repository.updateRequest(updated)
            _activeClientRequest.value = null
            _matchedEngineer.value = null
            _problemDescription.value = ""
            _aiTriageResult.value = null
            _selectedCategory.value = null
            _currentClientScreen.value = "HOME"
        }
    }

    fun submitRatingAndFeedback(reqId: Int, rating: Int, feedback: String) {
        viewModelScope.launch {
            val req = repository.getRequestById(reqId) ?: return@launch
            val updated = req.copy(
                rating = rating,
                feedback = feedback,
                status = "COMPLETED"
            )
            repository.updateRequest(updated)
            
            // Adjust engineer's average rating slightly
            if (req.matchedEngineerId != null) {
                val eng = repository.getEngineerById(req.matchedEngineerId)
                if (eng != null) {
                    val newRating = ((eng.rating * 9) + rating) / 10f
                    repository.updateEngineer(eng.copy(rating = newRating))
                }
            }

            _activeClientRequest.value = null
            _matchedEngineer.value = null
            _problemDescription.value = ""
            _aiTriageResult.value = null
            _selectedCategory.value = null
            _currentClientScreen.value = "HOME"
        }
    }

    fun cancelActiveRequest(reason: String = "Cancelado pelo cliente") {
        val req = _activeClientRequest.value ?: return
        viewModelScope.launch {
            simulationJob?.cancel()
            val updated = req.copy(status = "COMPLETED", feedback = "Cancelado pelo cliente: $reason")
            repository.updateRequest(updated)
            _activeClientRequest.value = null
            _matchedEngineer.value = null
            _currentClientScreen.value = "HOME"
        }
    }

    fun resetApp() {
        viewModelScope.launch {
            repository.clearRequests()
            _activeClientRequest.value = null
            _matchedEngineer.value = null
            _problemDescription.value = ""
            _selectedCategory.value = null
            _aiTriageResult.value = null
            _selectedRole.value = "NONE"
            _currentClientScreen.value = "HOME"
            _currentProfessionalScreen.value = "LOGIN"
        }
    }
}

// ViewModel Factory
class BandaViewModelFactory(
    private val application: Application,
    private val repository: BandaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BandaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BandaViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
