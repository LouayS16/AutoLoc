package tn.esprit.autoloc;

public class TestExecutionVerification {

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("         DEMARRAGE DE LA VERIFICATION COMPLETE DES SERVICES D'AFFECTATION       ");
        System.out.println("================================================================================");

        int passed = 0;
        int failed = 0;

        AffectationServicesTest affectationTest = new AffectationServicesTest();

        // Service 1: affecterReservationAClient
        try {
            System.out.println("\n[SERVICE 1] affecterReservationAClient(Long idReservation, String nom, String prenom)");
            affectationTest.setUp();
            affectationTest.testAffecterReservationAClient_Success();
            System.out.println("--> SUCCES: ReservationResponseDTO retourne avec succes (Client affecte a la reservation)");
            passed++;
        } catch (Exception e) {
            System.err.println("--> ECHEC: " + e.getMessage());
            failed++;
        }

        // Service 2: affecterVehiculeAEquipement
        try {
            System.out.println("\n[SERVICE 2] affecterVehiculeAEquipement(Long idVehicule, Long idEquipement)");
            affectationTest.setUp();
            affectationTest.testAffecterVehiculeAEquipement_Success();
            System.out.println("--> SUCCES: VehiculeDTO retourne avec la liste des equipements mise a jour");
            passed++;
        } catch (Exception e) {
            System.err.println("--> ECHEC: " + e.getMessage());
            failed++;
        }

        // Service 3: affecterMaintenanceAvehicule
        try {
            System.out.println("\n[SERVICE 3] affecterMaintenanceAvehicule(String description, String immatriculation)");
            affectationTest.setUp();
            affectationTest.testAffecterMaintenanceAvehicule_Success();
            System.out.println("--> SUCCES: VehiculeDTO retourne avec statut 'MAINTENANCE' et nouvelle fiche de maintenance");
            passed++;
        } catch (Exception e) {
            System.err.println("--> ECHEC: " + e.getMessage());
            failed++;
        }

        // Service 4: affecterContratAReservation
        try {
            System.out.println("\n[SERVICE 4] affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin)");
            affectationTest.setUp();
            affectationTest.testAffecterContratAReservation_Success();
            System.out.println("--> SUCCES: ContratDTO retourne avec montant total calcule (tarif * nbJours) et statut valide");
            passed++;
        } catch (Exception e) {
            System.err.println("--> ECHEC: " + e.getMessage());
            failed++;
        }

        // Service 5: creerReservationEtContratAssocie
        try {
            System.out.println("\n[SERVICE 5] creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO)");
            affectationTest.setUp();
            affectationTest.testCreerReservationEtContratAssocie_Success();
            System.out.println("--> SUCCES: ReservationResponseDTO retourne avec les IDs et donnees de la reservation et du contrat");
            passed++;
        } catch (Exception e) {
            System.err.println("--> ECHEC: " + e.getMessage());
            failed++;
        }

        System.out.println("\n================================================================================");
        System.out.println(String.format("          BILAN DES SERVICES D'AFFECTATION : %d REUSSIS / %d ECHECS", passed, failed));
        System.out.println("================================================================================");
    }
}
