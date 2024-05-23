import java.util.HashMap;

public class UserServices {
    /* static HashMap to keep track of all users. Static means that there is only
     * one copy of the HashMap for all instances of the class.
     */
    public static HashMap<String,User> users = new HashMap<String,User>();

    /* find
     * Given a String, nm, this method returns the User with that name. If no
     * such user exists, the method returns null.
     */
    public static User find( String nm ) {
        return users.get(nm);
    }

    /* leave
     * This method removes the user from the social network. It removes the user
     * from the static HashMap and removes the user from all of their friends'
     * adj.
     */
    public void leave(String name) {
        User u = find(name);
        users.remove( name );
        for( User v : u.getAdjValues() ) {
            v.removeAdjAt( name );
        }
        for( User f : u.getFlwValues() ) {
            f.removeFlwAt( name );
        }
    }
}
