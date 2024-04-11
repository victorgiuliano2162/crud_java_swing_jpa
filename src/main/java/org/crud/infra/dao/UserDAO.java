package org.crud.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserDAO implements IUserDAO{



    private Map<Long, User> map;



    public UserDAO() {
        this.map = new HashMap<>();
    }
    @Override
    public Boolean cadastrar(User u) {
        if(this.map.containsKey(u.getCpf())){
            //Alterar pra JOptionPane
            System.out.println("Usuário já cadastrado");
            //Pensar na possibilidade de criar uma método que verifique as operação e retorne strings informado o estado das operações
            return false;
        }
        this.map.put(u.getCpf(), u);
        //Alterar pra JOptionPane
        System.out.println("Usuário cadastrado com sucesso");
        return true;
    }

    @Override
    public void excluir(Long cpf) {
        User userCadastrado = this.map.get(cpf);
        if(userCadastrado != null) {
            this.map.remove(userCadastrado.getCpf(), userCadastrado);
        }
    }

    @Override
    public void alterar(User u) {
        User userCadastrado = this.map.get(u.getCpf());
        if(userCadastrado != null){
            userCadastrado.setNome(u.getNome());
            userCadastrado.setTel(u.getTel());
            userCadastrado.setNumero(u.getNumero());
            userCadastrado.setEnd(u.getEnd());
            userCadastrado.setCidade(u.getCidade());
            userCadastrado.setEstado(u.getEstado());
        }
    }

    @Override
    public User consultar(Long cpf) {
        return this.map.get(cpf);
    }

    @Override
    public Collection<User> buscarTodos() {
        return this.map.values();
    }
}
