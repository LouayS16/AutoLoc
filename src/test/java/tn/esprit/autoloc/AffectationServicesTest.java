package tn.esprit.autoloc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.dto.*;
import tn.esprit.autoloc.mapper.ContratMapper;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.*;
import tn.esprit.autoloc.service.*;

import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AffectationServicesTest {

    private IReservationService reservationService;
    private IVehiculeService vehiculeService;
    private IContratService contratService;

    // Entités de test partagées
    private Client testClient;
    private Vehicule testVehicule;
    private Reservation testReservation;
    private Equipement testEquipement;
    private Contrat testContrat;

    @BeforeEach
    public void setUp() {
        testClient = new Client();
        testClient.setIdClient(1L);
        testClient.setNom("Ben Salah");
        testClient.setPrenom("Ahmed");

        testVehicule = new Vehicule();
        testVehicule.setIdVehicule(5L);
        testVehicule.setImmatriculation("123-TN-456");
        testVehicule.setMarque("Renault");
        testVehicule.setModele("Clio");
        testVehicule.setCategorie(CategorieVehicule.CITADINE);
        testVehicule.setTarifJournalier(new BigDecimal("90.00"));
        testVehicule.setStatut(StatutVehicule.DISPONIBLE);
        testVehicule.setEquipements(new ArrayList<>());

        testReservation = new Reservation();
        testReservation.setIdReservation(10L);
        testReservation.setDateDebut(LocalDate.of(2026, 6, 1));
        testReservation.setDateFin(LocalDate.of(2026, 6, 5));
        testReservation.setStatut(StatutReservation.CONFIRMEE);
        testReservation.setVehicule(testVehicule);

        testEquipement = new Equipement();
        testEquipement.setIdEquipement(2L);
        testEquipement.setLibelle("GPS Navigation");

        testContrat = new Contrat();
        testContrat.setIdContrat(99L);
        testContrat.setReservation(testReservation);

        // Dynamic Proxy pour ClientRepository
        ClientRepository clientRepo = (ClientRepository) Proxy.newProxyInstance(
                ClientRepository.class.getClassLoader(),
                new Class<?>[]{ClientRepository.class},
                (proxy, method, args) -> {
                    String name = method.getName();
                    if ("findFirstByNomAndPrenom".equals(name) || "findByNomAndPrenom".equals(name)) {
                        String nom = (String) args[0];
                        String prenom = (String) args[1];
                        if ("Ben Salah".equalsIgnoreCase(nom) && "Ahmed".equalsIgnoreCase(prenom)) {
                            return Optional.of(testClient);
                        }
                        return Optional.empty();
                    }
                    if ("findById".equals(name)) {
                        Long id = (Long) args[0];
                        return id.equals(1L) ? Optional.of(testClient) : Optional.empty();
                    }
                    return null;
                }
        );

        // Dynamic Proxy pour VehiculeRepository
        VehiculeRepository vehiculeRepo = (VehiculeRepository) Proxy.newProxyInstance(
                VehiculeRepository.class.getClassLoader(),
                new Class<?>[]{VehiculeRepository.class},
                (proxy, method, args) -> {
                    String name = method.getName();
                    if ("findById".equals(name)) {
                        Long id = (Long) args[0];
                        return id.equals(5L) ? Optional.of(testVehicule) : Optional.empty();
                    }
                    if ("findByImmatriculation".equals(name)) {
                        String immat = (String) args[0];
                        return "123-TN-456".equalsIgnoreCase(immat) ? Optional.of(testVehicule) : Optional.empty();
                    }
                    if ("save".equals(name)) {
                        return args[0];
                    }
                    return null;
                }
        );

        // Dynamic Proxy pour ReservationRepository
        ReservationRepository reservationRepo = (ReservationRepository) Proxy.newProxyInstance(
                ReservationRepository.class.getClassLoader(),
                new Class<?>[]{ReservationRepository.class},
                (proxy, method, args) -> {
                    String name = method.getName();
                    if ("findById".equals(name)) {
                        Long id = (Long) args[0];
                        return id.equals(10L) ? Optional.of(testReservation) : Optional.empty();
                    }
                    if ("findFirstByDateDebutAndDateFin".equals(name) || "findByDateDebutAndDateFin".equals(name)) {
                        LocalDate d1 = (LocalDate) args[0];
                        LocalDate d2 = (LocalDate) args[1];
                        if (d1.equals(testReservation.getDateDebut()) && d2.equals(testReservation.getDateFin())) {
                            return Optional.of(testReservation);
                        }
                        return Optional.empty();
                    }
                    if ("save".equals(name)) {
                        Reservation r = (Reservation) args[0];
                        if (r.getIdReservation() == null) {
                            r.setIdReservation(50L);
                        }
                        return r;
                    }
                    return null;
                }
        );

        // Dynamic Proxy pour ContratRepository
        ContratRepository contratRepo = (ContratRepository) Proxy.newProxyInstance(
                ContratRepository.class.getClassLoader(),
                new Class<?>[]{ContratRepository.class},
                (proxy, method, args) -> {
                    String name = method.getName();
                    if ("findByReservation".equals(name) || "findByReservationIdReservation".equals(name)) {
                        return Optional.empty();
                    }
                    if ("save".equals(name)) {
                        Contrat c = (Contrat) args[0];
                        if (c.getIdContrat() == null) {
                            c.setIdContrat(99L);
                        }
                        return c;
                    }
                    return null;
                }
        );

        // Dynamic Proxy pour EquipementRepository
        EquipementRepository equipementRepo = (EquipementRepository) Proxy.newProxyInstance(
                EquipementRepository.class.getClassLoader(),
                new Class<?>[]{EquipementRepository.class},
                (proxy, method, args) -> {
                    String name = method.getName();
                    if ("findById".equals(name)) {
                        Long id = (Long) args[0];
                        return id.equals(2L) ? Optional.of(testEquipement) : Optional.empty();
                    }
                    return null;
                }
        );

        // Dynamic Proxy pour MaintenanceRepository
        MaintenanceRepository maintenanceRepo = (MaintenanceRepository) Proxy.newProxyInstance(
                MaintenanceRepository.class.getClassLoader(),
                new Class<?>[]{MaintenanceRepository.class},
                (proxy, method, args) -> {
                    String name = method.getName();
                    if ("save".equals(name)) {
                        Maintenance m = (Maintenance) args[0];
                        m.setIdMaintenance(15L);
                        return m;
                    }
                    return null;
                }
        );

        ReservationServiceImpl resServiceImpl = new ReservationServiceImpl();
        VehiculeServiceImpl vehServiceImpl = new VehiculeServiceImpl();
        ContratServiceImpl contServiceImpl = new ContratServiceImpl();

        ReservationMapper resMapper = new ReservationMapper();
        VehiculeMapper vehMapper = new VehiculeMapper();
        ContratMapper contMapper = new ContratMapper();

        ReflectionTestUtils.setField(resServiceImpl, "reservationRepository", reservationRepo);
        ReflectionTestUtils.setField(resServiceImpl, "vehiculeRepository", vehiculeRepo);
        ReflectionTestUtils.setField(resServiceImpl, "clientRepository", clientRepo);
        ReflectionTestUtils.setField(resServiceImpl, "contratRepository", contratRepo);
        ReflectionTestUtils.setField(resServiceImpl, "reservationMapper", resMapper);

        ReflectionTestUtils.setField(vehServiceImpl, "vehiculeRepository", vehiculeRepo);
        ReflectionTestUtils.setField(vehServiceImpl, "equipementRepository", equipementRepo);
        ReflectionTestUtils.setField(vehServiceImpl, "maintenanceRepository", maintenanceRepo);
        ReflectionTestUtils.setField(vehServiceImpl, "vehiculeMapper", vehMapper);

        ReflectionTestUtils.setField(contServiceImpl, "contratRepository", contratRepo);
        ReflectionTestUtils.setField(contServiceImpl, "reservationRepository", reservationRepo);
        ReflectionTestUtils.setField(contServiceImpl, "contratMapper", contMapper);

        this.reservationService = resServiceImpl;
        this.vehiculeService = vehServiceImpl;
        this.contratService = contServiceImpl;
    }

    @Test
    @DisplayName("1. affecterReservationAClient - succès")
    public void testAffecterReservationAClient_Success() {
        ReservationResponseDTO response = reservationService.affecterReservationAClient(10L, "Ben Salah", "Ahmed");

        assertNotNull(response);
        assertEquals(10L, response.getIdReservation());
        assertEquals("Ben Salah", response.getNomClient());
        assertEquals("Ahmed", response.getPrenomClient());
    }

    @Test
    @DisplayName("2. affecterVehiculeAEquipement - succès")
    public void testAffecterVehiculeAEquipement_Success() {
        VehiculeDTO dto = vehiculeService.affecterVehiculeAEquipement(5L, 2L);

        assertNotNull(dto);
        assertEquals(5L, dto.getIdVehicule());
        assertTrue(dto.getEquipementIds().contains(2L));
    }

    @Test
    @DisplayName("3. affecterMaintenanceAvehicule - succès")
    public void testAffecterMaintenanceAvehicule_Success() {
        VehiculeDTO dto = vehiculeService.affecterMaintenanceAvehicule("Changement plaquettes", "123-TN-456");

        assertNotNull(dto);
        assertEquals(StatutVehicule.MAINTENANCE, dto.getStatut());
        assertEquals("123-TN-456", dto.getImmatriculation());
    }

    @Test
    @DisplayName("4. affecterContratAReservation - succès")
    public void testAffecterContratAReservation_Success() {
        ContratDTO contratDTO = contratService.affecterContratAReservation(
                LocalDate.of(2026, 5, 28),
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 5)); // 4 jours

        assertNotNull(contratDTO);
        assertEquals(99L, contratDTO.getIdContrat());
        assertEquals(10L, contratDTO.getIdReservation());
        assertEquals(new BigDecimal("360.00"), contratDTO.getMontantTotal()); // 4 * 90.00
        assertTrue(contratDTO.getValide());
    }

    @Test
    @DisplayName("5. creerReservationEtContratAssocie - succès")
    public void testCreerReservationEtContratAssocie_Success() {
        ReservationWithContratRequestDTO request = ReservationWithContratRequestDTO.builder()
                .dateDebut(LocalDate.of(2026, 9, 1))
                .dateFin(LocalDate.of(2026, 9, 4)) // 3 jours
                .idVehicule(5L)
                .idClient(1L)
                .dateSignature(LocalDate.of(2026, 8, 25))
                .build();

        ReservationResponseDTO response = reservationService.creerReservationEtContratAssocie(request);

        assertNotNull(response);
        assertEquals(50L, response.getIdReservation());
        assertEquals(99L, response.getIdContrat());
        assertEquals(5L, response.getIdVehicule());
        assertEquals("123-TN-456", response.getImmatriculationVehicule());
        assertEquals(1L, response.getIdClient());
        assertEquals("Ben Salah", response.getNomClient());
        assertEquals(new BigDecimal("270.00"), response.getMontantTotalContrat()); // 3 * 90.00
    }
}
