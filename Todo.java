package fr2.m2i.todoliste;

public class Todo {
    private int id;
    private Urgence urgence;
    private String titre;
    private String description;

    public Todo() {}

    public Todo(Urgence urgence, String titre, String description) {
        this.urgence = urgence;
        this.titre = titre;
        this.description = description;
    }

    public Todo(String urgence, String titre, String description) {
        this.urgence = Urgence.valueOf(urgence);
        this.titre = titre;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Urgence geturgence() {
        return urgence;
    }


    public void seturgence(Urgence urgence) {
        this.urgence = urgence;
    }

    public String gettitre() {
        return titre;
    }

    public void settitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
