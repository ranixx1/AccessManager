public class Setor {
    private final Long id;
    private String nome;

    public Setor(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public Long getId(){
        return id;
    }
    public String Getnome(){
        return nome;   
    }
}
