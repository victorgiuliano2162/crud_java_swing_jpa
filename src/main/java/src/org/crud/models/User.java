package src.org.crud.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "user")
public class User {

    @Id
    @Getter @Setter
    private String cpf;

    @Getter @Setter
    @Column(name = "user_name", nullable = false, length = 200)
    private String nome;

    @Getter @Setter
    @Column(name = "user_cel",nullable = false, length = 200)
    private Long tel;

    @Getter @Setter
    @Column(name = "user_addres", nullable = false, length = 200)
    private String end;

    @Getter @Setter
    @Column(name = "user_addres_num", nullable = false, length = 200)
    private Integer numero;

    @Getter @Setter
    @Column(name = "user_city", nullable = false, length = 200)
    private String cidade;

    @Getter @Setter
    @Column(name = "user_state", nullable = false, length = 200)
    private String estado;

    public User(){}

    public User(String nome, String cpf, String tel, String end, String numero, String cidade, String estado) {
        this.nome = nome;
        this.cpf = cpf.trim().replaceAll("[^\\d.]", "");
        this.tel = Long.valueOf(tel.trim().replaceAll("[^\\d.]", ""));
        this.end = end;
        this.numero = Integer.valueOf(numero.trim().replaceAll("[^\\d.]", ""));
        this.cidade = cidade;
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(cpf, user.cpf);
    }

    
}
