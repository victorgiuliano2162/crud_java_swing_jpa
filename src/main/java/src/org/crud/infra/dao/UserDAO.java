package src.org.crud.infra.dao;



import src.org.crud.models.User;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserDAO<E> implements IUserDAO {

    public Map<String, User> map;

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private Class<E> entityClass;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("crud_java_swing_jpa");
            em = emf.createEntityManager();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void creatMap() {
            if (map == null) {
            map = new HashMap<>();
        }
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
    public void excluir(String cpf) {
        User u = em.find(User.class, cpf);
        abrirTransaction();
        em.remove(u);
        fecharTransaction();
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
    public User consultar(String cpf) {
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

    public User obterPorID(String cpf){
        if(em.find(User.class, cpf) != null){
        return em.find(User.class, cpf);
        }
        return null;
    }

    public void excluirAtomico(User u){
        abrirTransaction();
        em.find(User.class, u);
        em.remove(u);
        fecharTransaction();
    }

    public void fechar(){
        em.close();
    }
}
