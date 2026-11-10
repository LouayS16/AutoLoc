package tn.esprit.autoloc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.dto.AgenceDTO;
import tn.esprit.autoloc.dto.ClientDTO;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.controller.AgenceController;
import tn.esprit.autoloc.web.controller.ClientController;
import tn.esprit.autoloc.web.controller.VehiculeController;
import tn.esprit.autoloc.web.exception.GlobalExceptionHandler;
import tn.esprit.autoloc.web.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AutoLocControllerValidationTest {

    private MockMvc mockMvcClient;
    private MockMvc mockMvcVehicule;
    private MockMvc mockMvcAgence;

    private ObjectMapper objectMapper;

    private ClientController clientController;
    private VehiculeController vehiculeController;
    private AgenceController agenceController;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Stub pour IClientService
        IClientService clientServiceStub = new IClientService() {
            @Override
            public List<ClientDTO> retrieveAllClients() {
                ClientDTO dto = ClientDTO.builder()
                        .idClient(1L)
                        .nom("Ben Salah")
                        .prenom("Ahmed")
                        .email("ahmed.bensalah@esprit.tn")
                        .telephone("98123456")
                        .numPermis("TUN-123456")
                        .dateInscription(LocalDate.of(2026, 1, 1))
                        .build();
                return List.of(dto);
            }

            @Override
            public ClientDTO addClient(ClientDTO c) {
                return ClientDTO.builder()
                        .idClient(10L)
                        .nom(c.getNom())
                        .prenom(c.getPrenom())
                        .email(c.getEmail())
                        .telephone(c.getTelephone())
                        .numPermis(c.getNumPermis())
                        .dateInscription(c.getDateInscription())
                        .build();
            }

            @Override
            public ClientDTO updateClient(ClientDTO c) {
                return c;
            }

            @Override
            public ClientDTO retrieveClient(Long idClient) {
                if (idClient.equals(999L)) {
                    throw new ResourceNotFoundException("Client non trouvé avec l'identifiant : 999");
                }
                return ClientDTO.builder()
                        .idClient(idClient)
                        .nom("Ben Salah")
                        .prenom("Ahmed")
                        .email("ahmed.bensalah@esprit.tn")
                        .telephone("98123456")
                        .numPermis("TUN-123456")
                        .build();
            }

            @Override
            public void removeClient(Long idClient) {
            }

            @Override
            public List<ClientDTO> addClients(List<ClientDTO> clients) {
                return clients;
            }
        };

        // Stub pour IVehiculeService
        IVehiculeService vehiculeServiceStub = new IVehiculeService() {
            @Override
            public List<VehiculeDTO> retrieveAllVehicules() {
                return Collections.emptyList();
            }

            @Override
            public VehiculeDTO addVehicule(VehiculeDTO vehiculeDTO) {
                vehiculeDTO.setIdVehicule(1L);
                return vehiculeDTO;
            }

            @Override
            public VehiculeDTO updateVehicule(VehiculeDTO vehiculeDTO) {
                return vehiculeDTO;
            }

            @Override
            public VehiculeDTO retrieveVehicule(Long idVehicule) {
                return null;
            }

            @Override
            public void removeVehicule(Long idVehicule) {
            }
        };

        // Stub pour IAgenceService
        IAgenceService agenceServiceStub = new IAgenceService() {
            @Override
            public List<AgenceDTO> retrieveAllAgences() {
                return Collections.emptyList();
            }

            @Override
            public AgenceDTO addAgence(AgenceDTO agenceDTO) {
                agenceDTO.setIdAgence(1L);
                return agenceDTO;
            }

            @Override
            public AgenceDTO updateAgence(AgenceDTO agenceDTO) {
                return agenceDTO;
            }

            @Override
            public AgenceDTO retrieveAgence(Long idAgence) {
                return null;
            }

            @Override
            public void removeAgence(Long idAgence) {
            }
        };

        clientController = new ClientController();
        ReflectionTestUtils.setField(clientController, "clientService", clientServiceStub);

        vehiculeController = new VehiculeController();
        ReflectionTestUtils.setField(vehiculeController, "vehiculeService", vehiculeServiceStub);

        agenceController = new AgenceController();
        ReflectionTestUtils.setField(agenceController, "agenceService", agenceServiceStub);

        mockMvcClient = MockMvcBuilders.standaloneSetup(clientController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        mockMvcVehicule = MockMvcBuilders.standaloneSetup(vehiculeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        mockMvcAgence = MockMvcBuilders.standaloneSetup(agenceController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("GET /api/clients - Doit retourner la liste des DTOs avec statut 200 OK")
    public void testRetrieveAllClients_ReturnsDtoList() throws Exception {
        mockMvcClient.perform(get("/api/clients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idClient").value(1))
                .andExpect(jsonPath("$[0].nom").value("Ben Salah"))
                .andExpect(jsonPath("$[0].email").value("ahmed.bensalah@esprit.tn"));
    }

    @Test
    @DisplayName("POST /api/clients - Validation échoue lorsque nom et email sont invalides (400 Bad Request)")
    public void testAddClient_ValidationFailure() throws Exception {
        // Nom vide et email malformé
        ClientDTO invalidDto = ClientDTO.builder()
                .nom("")
                .prenom("Ali")
                .email("email-invalide")
                .telephone("12345678")
                .numPermis("P12345")
                .build();

        mockMvcClient.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.validationErrors.nom").exists())
                .andExpect(jsonPath("$.validationErrors.email").exists());
    }

    @Test
    @DisplayName("POST /api/clients - Validation réussie et retour du DTO (201 Created)")
    public void testAddClient_Success() throws Exception {
        ClientDTO requestDto = ClientDTO.builder()
                .nom("Trabelsi")
                .prenom("Sarra")
                .email("sarra.trabelsi@esprit.tn")
                .telephone("22114455")
                .numPermis("B998877")
                .dateInscription(LocalDate.of(2026, 1, 15))
                .build();

        mockMvcClient.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idClient").value(10))
                .andExpect(jsonPath("$.nom").value("Trabelsi"))
                .andExpect(jsonPath("$.email").value("sarra.trabelsi@esprit.tn"));
    }

    @Test
    @DisplayName("GET /api/clients/{id} - Ressource inexistante retourne 404 géré par GlobalExceptionHandler")
    public void testRetrieveClient_NotFound() throws Exception {
        mockMvcClient.perform(get("/api/clients/999"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Client non trouvé avec l'identifiant : 999"));
    }

    @Test
    @DisplayName("POST /api/vehicules - Validation tarifJournalier > 0 et champs obligatoires")
    public void testAddVehicule_ValidationFailure() throws Exception {
        VehiculeDTO invalidVehicule = VehiculeDTO.builder()
                .immatriculation("200-TN-1234")
                .marque("Volkswagen")
                .modele("Golf 8")
                .categorie(CategorieVehicule.BERLINE)
                .tarifJournalier(new BigDecimal("-50.0"))
                .statut(StatutVehicule.DISPONIBLE)
                .idAgence(null)
                .build();

        mockMvcVehicule.perform(post("/api/vehicules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidVehicule)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.validationErrors.tarifJournalier").exists())
                .andExpect(jsonPath("$.validationErrors.idAgence").exists());
    }

    @Test
    @DisplayName("POST /api/agences - Validation nom obligatoire")
    public void testAddAgence_ValidationFailure() throws Exception {
        AgenceDTO invalidAgence = AgenceDTO.builder()
                .nom("")
                .ville("Tunis")
                .adresse("Avenue Habib Bourguiba")
                .telephone("71000111")
                .build();

        mockMvcAgence.perform(post("/api/agences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAgence)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.validationErrors.nom").value("Le nom de l'agence est obligatoire"));
    }
}
