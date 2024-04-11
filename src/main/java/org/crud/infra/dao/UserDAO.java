package org.crud.infra.dao;

import org.crud.models.User;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserDAO<E> implements IUserDAO {

    private Map<Long, User> map;

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private Class<E> entityClass;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("crud_java_swing_jpa");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void getMap() {
        this.map = new HashMap<>();
    }

    @Override
    public Boolean cadastrar(User u) {
        abrirTransaction();
        if(this.map.containsKey(u.getCpf())){
            //Alterar pra JOptionPane
            System.out.println("Usuário já cadastrado");
            //Pensar na possibilidade de criar uma método que verifique as operação e retorne strings informado o estado das operações
            fechar();
            return false;
        }
        this.map.put(u.getCpf(), u);
        //Alterar pra JOptionPane
        System.out.println("Usuário cadastrado com sucesso");
        incluir((E) u);
        fecharTransaction();
        return true;
    }

    @Override
    public void excluir(Long cpf) {
        User userCadastrado = this.map.get(cpf);
        if(userCadastrado != null) {
            this.map.remove(userCadastrado.getCpf(), userCadastrado);
            excluirAtomico(userCadastrado);
        }
        fechar();
    }

    @Override
    public void alterar(User u) {
        abrirTransaction();
        User userCadastrado = this.map.get(u.getCpf());
        if(obterPorID(u.getCpf()) != null){
            userCadastrado.setNome(u.getNome());
            userCadastrado.setTel(u.getTel());
            userCadastrado.setNumero(u.getNumero());
            userCadastrado.setEnd(u.getEnd());
            userCadastrado.setCidade(u.getCidade());
            userCadastrado.setEstado(u.getEstado());
            incluir((E) u);
            fecharTransaction();

        }
        fechar();;
    }

    @Override
    public User consultar(Long cpf) {
        if(obterPorID(cpf) != null) {
        return this.map.get(cpf);
        }
        return null;
    }

    @Override
    public Collection<User> buscarTodos() {
        return this.map.values();
    }

    public UserDAO<E> abrirTransaction() {
        em.getTransaction().begin();
        return this;
    }

    public UserDAO<E> fecharTransaction() {
        em.getTransaction().commit();
        return this;
    }

    public UserDAO<E> incluir(E e){
        em.persist(e);
        return this;
    }

    public UserDAO<E> incluirAtomico(E e){
        return this.abrirTransaction().incluir(e).fecharTransaction();
    }

    public User obterPorID(Long cpf){
        if(em.find(User.class, cpf) != null){
        return em.find(User.class, cpf);
        }
        return null;
    }

    public void excluirAtomico(User u){
        abrirTransaction();
        em.remove(u);
        fecharTransaction();
    }

    public void fechar(){
        em.close();
    }
}
