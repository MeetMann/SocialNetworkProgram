import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServicesTest {

    private static String[] friendNames = {"Alice", "Bob", "Carol", "Dave", "Eve", "Frank", "Grace"};

    // clear the static users HashMap before each test
    @AfterEach
    void clearUsers() {
        User.users.clear();
    }

    // Test finding a user that exists returns that user
    @Test
    void testFind() {
        User u = new User(friendNames[0]);
        User v = User.find(friendNames[0]);
        assertEquals(u, v, "find returned the wrong user");
    }

    // Test finding a user that does not exist returns null
    @Test
    void testFindNotExists() {
        User u = User.find(friendNames[0]);
        assertEquals(null, u, "find returned a user that does not exist");
    }

    // Test find returns the correct user when there are multiple users
    @Test
    void testFindMultiple() {
        User u = new User(friendNames[0]);
        User v = new User(friendNames[1]);
        User w = new User(friendNames[2]);
        User x = User.find(friendNames[1]);
        assertEquals(u, User.find(friendNames[0]), "find returned the wrong user for u");
        assertEquals(v, User.find(friendNames[1]), "find returned the wrong user for v");
        assertEquals(w, User.find(friendNames[2]), "find returned the wrong user for w");
    }

    // Test a single user leaving removes them from the HashMap
    @Test
    void testLeaveSingle() {
        User u = new User(friendNames[0]);
        u.leave(friendNames[0]);
        assertEquals(0, u.getAdjSize(), "u's adj has the wrong size");
        assertEquals(0, u.getFlwSize(), "u's flw has the wrong size");
        assertEquals(0, User.users.size(), "Incorrect size");
        assertEquals(null, User.users.get(friendNames[0]), "User not removed from HashMap");
    }

    /* Test a user leaving when they have a friend removes them from the HashMap
     * and removes them from their friend's adj.
     */
    @Test
    void testLeaveFriend() {
        User u = new User(friendNames[0]);
        User v = new User(friendNames[1]);
        u.friend(friendNames[1]);
        u.leave(friendNames[0]);
        assertEquals(0, v.getAdjSize(), "v's adj has the wrong size");
        assertFalse(v.getAdjValues().contains(friendNames[0]), "v's adj has the wrong user");
        assertEquals(1, User.users.size(), "Incorrect size");
        assertEquals(null, User.users.get(friendNames[0]), "User not removed from HashMap");
        assertEquals(v, User.users.get(friendNames[1]), "User v not in HashMap");
    }
}