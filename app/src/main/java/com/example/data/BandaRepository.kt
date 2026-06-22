package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class BandaRepository(private val database: BandaDatabase) {
    private val engineerDao = database.engineerDao()
    private val serviceRequestDao = database.serviceRequestDao()

    val allEngineers: Flow<List<Engineer>> = engineerDao.getAllEngineers()
    val allOnlineEngineers: Flow<List<Engineer>> = engineerDao.getAllOnlineEngineers()
    val allRequests: Flow<List<ServiceRequest>> = serviceRequestDao.getAllRequests()
    val activeRequests: Flow<List<ServiceRequest>> = serviceRequestDao.getActiveRequests()

    fun getOnlineEngineersBySpecialty(specialty: String): Flow<List<Engineer>> {
        return engineerDao.getOnlineEngineersBySpecialty(specialty)
    }

    fun getRequestsByClient(clientPhone: String): Flow<List<ServiceRequest>> {
        return serviceRequestDao.getRequestsByClient(clientPhone)
    }

    fun getActiveRequestsForEngineer(engineerId: Int): Flow<List<ServiceRequest>> {
        return serviceRequestDao.getActiveRequestsForEngineer(engineerId)
    }

    suspend fun getEngineerById(id: Int): Engineer? {
        return engineerDao.getEngineerById(id)
    }

    suspend fun getEngineerByPhone(phone: String): Engineer? {
        return engineerDao.getEngineerByPhone(phone)
    }

    suspend fun getEngineerByPhoneOrEmail(identifier: String): Engineer? {
        return engineerDao.getEngineerByPhoneOrEmail(identifier)
    }

    suspend fun insertEngineer(engineer: Engineer): Long {
        return engineerDao.insertEngineer(engineer)
    }

    suspend fun updateEngineer(engineer: Engineer) {
        engineerDao.updateEngineer(engineer)
    }

    suspend fun getRequestById(id: Int): ServiceRequest? {
        return serviceRequestDao.getRequestById(id)
    }

    suspend fun insertRequest(request: ServiceRequest): Long {
        return serviceRequestDao.insertRequest(request)
    }

    suspend fun updateRequest(request: ServiceRequest) {
        serviceRequestDao.updateRequest(request)
    }

    suspend fun clearRequests() {
        serviceRequestDao.clearAll()
    }

    suspend fun prepopulateMockEngineersIfNeeded() {
        // Run check on a flow block
        val existing = engineerDao.getAllEngineers().first()
        if (existing.isEmpty()) {
            val mockInformatico1 = Engineer(
                name = "Yara Fragoso",
                phone = "922222222",
                specialty = "Informático",
                skills = "Desenvolvimento de Software, Redes de Computadores, Administração de Servidores Linux e Cloud",
                certId = "EB-2026-I12",
                experienceYears = 5,
                description = "Engenheira informática licenciada com experiência em infraestruturas digitais e segurança cibernética.",
                isOnline = true,
                latitude = -8.8320,
                longitude = 13.2290,
                rating = 4.8f
            )
            val mockInformatico2 = Engineer(
                name = "Sebastião Cassinda",
                phone = "924444444",
                specialty = "Informático",
                skills = "Bancos de Dados SQL/NoSQL, Administração de Servidores, APIs REST, Segurança de Redes corporativas",
                certId = "EB-2026-I40",
                experienceYears = 7,
                description = "Especialista em infraestrutura de TI e desenvolvimento backend. Soluções robustas para servidores locais e na nuvem.",
                isOnline = true,
                latitude = -8.8385,
                longitude = 13.2410,
                rating = 4.9f
            )
            val mockTecnicoTI = Engineer(
                name = "Pedro Capaxe",
                phone = "923333333",
                specialty = "Técnico de TI",
                skills = "Reparação de Portáteis, Redes Wi-Fi, Configuração de Roteadores, Formatação de Windows/macOS",
                certId = "EB-2026-T88",
                experienceYears = 6,
                description = "Técnico de suporte altamente ágil e atencioso. Especializado em resolver problemas urgentes de hardware e comunicações.",
                isOnline = true,
                latitude = -8.8450,
                longitude = 13.2260,
                rating = 4.7f
            )
            val mockTecnicoTI2 = Engineer(
                name = "Alzira Mendes",
                phone = "925555555",
                specialty = "Técnico de TI",
                skills = "Montagem de Computadores, Limpeza Física de Hardware, Troca de Telas/Teclados, Repetidores Wi-Fi",
                certId = "EB-2026-T15",
                experienceYears = 4,
                description = "Técnica focada em suporte ao hardware portátil e desktop. Rápido e eficiente com garantia de qualidade.",
                isOnline = true,
                latitude = -8.8290,
                longitude = 13.2380,
                rating = 4.6f
            )
            engineerDao.insertEngineer(mockInformatico1)
            engineerDao.insertEngineer(mockInformatico2)
            engineerDao.insertEngineer(mockTecnicoTI)
            engineerDao.insertEngineer(mockTecnicoTI2)
        }
    }
}
