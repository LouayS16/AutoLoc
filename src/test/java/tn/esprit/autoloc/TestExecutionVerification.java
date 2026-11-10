package tn.esprit.autoloc;

public class TestExecutionVerification {

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("         DEMARRAGE DE LA VERIFICATION COMPLETE DES DTO ET VALIDATIONS          ");
        System.out.println("================================================================================");

        AutoLocControllerValidationTest test = new AutoLocControllerValidationTest();

        int passed = 0;
        int failed = 0;

        // Test 1: GET /api/clients
        try {
            System.out.println("\n[TEST 1] GET /api/clients - Transformation en DTO");
            test.setUp();
            test.testRetrieveAllClients_ReturnsDtoList();
            System.out.println("--> SUCCES: La reponse renvoie bien la liste de ClientDTO avec statut 200 OK");
            passed++;
        } catch (Exception e) {
            System.err.println("--> ECHEC: " + e.getMessage());
            failed++;
        }

        // Test 2: POST /api/clients - Validation des donnees
        try {
            System.out.println("\n[TEST 2] POST /api/clients - Validation Starter et controles de saisie (nom vide, email invalide)");
            test.setUp();
            test.testAddClient_ValidationFailure();
            System.out.println("--> SUCCES: Requete 400 Bad Request retournee avec la structure JSON d'erreurs centralisee");
            passed++;
        } catch (Exception e) {
            System.err.println("--> ECHEC: " + e.getMessage());
            failed++;
        }

        // Test 3: POST /api/clients - Succes avec DTO
        try {
            System.out.println("\n[TEST 3] POST /api/clients - Creation valide avec ClientDTO");
            test.setUp();
            test.testAddClient_Success();
            System.out.println("--> SUCCES: Statut 201 Created retourne avec le ClientDTO enregistre");
            passed++;
        } catch (Exception e) {
            System.err.println("--> ECHEC: " + e.getMessage());
            failed++;
        }

        // Test 4: GET /api/clients/999 - Gestion centralisee 404
        try {
            System.out.println("\n[TEST 4] GET /api/clients/999 - Gestion centralisee des erreurs (ResourceNotFoundException)");
            test.setUp();
            test.testRetrieveClient_NotFound();
            System.out.println("--> SUCCES: Statut 404 Not Found retourne avec le format ErrorResponse centralise");
            passed++;
        } catch (Exception e) {
            System.err.println("--> ECHEC: " + e.getMessage());
            failed++;
        }

        // Test 5: POST /api/vehicules - Validation champs obligatoires et prix positif
        try {
            System.out.println("\n[TEST 5] POST /api/vehicules - Validation VehiculeDTO (tarif <= 0, agence absente)");
            test.setUp();
            test.testAddVehicule_ValidationFailure();
            System.out.println("--> SUCCES: Statut 400 Bad Request avec les erreurs specifiques sur tarifJournalier et idAgence");
            passed++;
        } catch (Exception e) {
            System.err.println("--> ECHEC: " + e.getMessage());
            failed++;
        }

        // Test 6: POST /api/agences - Validation AgenceDTO nom obligatoire
        try {
            System.out.println("\n[TEST 6] POST /api/agences - Validation AgenceDTO (nom obligatoire)");
            test.setUp();
            test.testAddAgence_ValidationFailure();
            System.out.println("--> SUCCES: Statut 400 Bad Request avec message d'erreur 'Le nom de l'agence est obligatoire'");
            passed++;
        } catch (Exception e) {
            System.err.println("--> ECHEC: " + e.getMessage());
            failed++;
        }

        System.out.println("\n================================================================================");
        System.out.println(String.format("                BILAN DES TESTS : %d REUSSIS / %d ECHECS", passed, failed));
        System.out.println("================================================================================");
    }
}

