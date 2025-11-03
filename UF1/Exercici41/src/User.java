import java.io.Serializable;

class User implements Serializable {
    String name;
    String pass;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }
}
