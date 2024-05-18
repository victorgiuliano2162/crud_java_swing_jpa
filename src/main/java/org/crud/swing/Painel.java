package org.crud.swing;


import org.crud.infra.dao.UserDAO;
import org.crud.models.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;


public class Painel extends Panel{

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 600;
    UserDAO userDAO = new UserDAO();

    public Painel(){
        this.setVisible(true);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.blue);
        this.setFocusable(true);

        userDAO.creatMap();

        startCrud(1);
    }

    public boolean isOpcaoValida(String opcao){
        if("1".equals(opcao) || "2".equals(opcao) || "3".equals(opcao) || "4".equals(opcao) || "5".equals(opcao) ||
                "6".equals(opcao)) return true;
        return false;
    }

    public User tratarDadosECadastrarUsuario(String dados) {
        if(dados == null) startCrud(0);
        String[] dadosSeparados = dados.split(",");
        if(dadosSeparados.length < 7 ) startCrud(2);
        String cpfTratado = dadosSeparados[1].trim().replaceAll("[^0-9]", "");

        User user = new User(dadosSeparados[0],cpfTratado,dadosSeparados[2],dadosSeparados[3],dadosSeparados[4],dadosSeparados[5],dadosSeparados[6]);
        userDAO.cadastrar(user);
        return user;
    }

    //Não está funcionando, talvez o problema esteja relacionado a tipagem do map
    //Problem solved :D
    public void excluirUsuario(String s) {
        if(s == null || "".equals(s)) startCrud(0);
        s.trim().replaceAll("[^\\d.]", "");
        userDAO.excluir(s);
    }

    public User consultarUsuario(String s) {
        String sTratado = s.trim().replaceAll("[^0-9]", "");
        if(userDAO.obterPorID(sTratado) == null) {
            //startCrud(3);
            return null;
        }
        return userDAO.obterPorID(sTratado);
    }

    public List<String> consultarTodos() {
        List<String> users= new ArrayList<>();
        List <User> usersDoBanco = userDAO.buscarTodos();

        for (User u : usersDoBanco) {
            String tel = String.valueOf(u.getTel());
            String num = String.valueOf(u.getNumero());
            User emUso = new User(u.getNome(), u.getCpf(), tel, u.getEnd(), num, u.getCidade(), u.getEstado());
            System.out.println(u.getNome());
            users.add("\n"+emUso.toString());
        }
        return users;
    }

    //Implementar depois
    public List<String> userTratado() {
        List<String> usersName =consultarTodos();
        Consumer<User> nome = n -> n.toString();
        UnaryOperator<String> nomeCerto = nom -> (nom.toString());
        return null;
    }

    public void startCrud(int a) {
        String opcao = "";
        if (a == 1) {
        opcao = JOptionPane.showInputDialog(null,"Digite: \n1 para cadastro \n2 para consulta \n3 para exclusão \n4 para alteração \n5 para sair","Cadastro", JOptionPane.INFORMATION_MESSAGE);
        } else if(a == 0) {
            opcao = JOptionPane.showInputDialog(null,"Preencha os campos para efetuar o cadastro\nDigite: \n1 para cadastro \n2 para consulta \n3 para exclusão \n4 para alteração \n5 para sair","Cadastro", JOptionPane.INFORMATION_MESSAGE);
        } else if(a == 2) {
            opcao = JOptionPane.showInputDialog(null,"Os campos em branco devem constar como um espaço em branco entre vírgulas.\nDigite na ordem:\nnome, cpf, telefone, endereço, número, cidade, estado\nDigite: \n1 para cadastro \n2 para consulta \n3 para exclusão \n4 para alteração \n5 para sair","Cadastro", JOptionPane.INFORMATION_MESSAGE);
        } else if(a == 3) {
            String consulta = JOptionPane.showInputDialog(null,"Consultar usuário\nDeseja fazer outra consultar?\n1 - Sim\n2 - Voltar ao início\n3 - Sair do programa","Usuário não encontrado no sistema", JOptionPane.INFORMATION_MESSAGE);
            if(consulta.equals("1")) opcao = "2";
            if(consulta.equals("2")) opcao = "1";
            if(consulta.equals("3")) opcao = "5";
        } else if(a == 4) {
            //exclusao
        } else if(a == 5) {
            //modificação
        }

        while(!isOpcaoValida(opcao)){
            if("".equals(opcao) || !isOpcaoValida(opcao)) {
                opcao = JOptionPane.showInputDialog(null,"Digite: \n1 para cadastro \n2 para consulta \n3 para exclusão \n4 para alteração \n5 para sair \nO valor digitado deve estar entre 1 e 5","Cadastro", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if(opcao.equals("1")){
            //cadastrar
            String dados = JOptionPane.showInputDialog(null, "Digite os dados do cliente separados por vírgula, na seguinte ordem: nome, cpf, telefone, endereço, número, cidade, estado", "Cadastrar usuário", JOptionPane.INFORMATION_MESSAGE);
            User u = tratarDadosECadastrarUsuario(dados);
            if(u != null) {
                dados =  JOptionPane.showInputDialog(null, "Usuário cadastrado com sucesso.\nDigite: \n1 -> para retornar ao menu\n2 -> para sair do aplicativo", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                if(dados.equals("1")) startCrud(1);
                if(dados.equals("2")) startCrud(5);
            }

        } else if(opcao.equals("2")){
            //consultar
            String dados = JOptionPane.showInputDialog(null, "Digite o cpf do usuário", "Consultar usuario", JOptionPane.INFORMATION_MESSAGE);
            if (consultarUsuario(dados) == null) {
                dados = null;
                startCrud(3);
            } else if (consultarUsuario(dados) != null) {
                JOptionPane.showMessageDialog(null, "Usuário cadastrado\n" + userDAO.toString(), "Consultar usuario",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if(opcao.equals("3")){
            //excluir
            String dados = JOptionPane.showInputDialog(null, "Digite o cpf do usuário que deseja excluir", "Exclusão de usuario", JOptionPane.INFORMATION_MESSAGE);
            excluirUsuario(dados);
        } else if(opcao.equals("4")){
            //alterar
            String dados = JOptionPane.showInputDialog(null, "Digite o cpf do usuário para modificar os dados", "Alteração de dados de usuario", JOptionPane.INFORMATION_MESSAGE);
            if (consultarUsuario(dados) == null) {
                dados = JOptionPane.showInputDialog(null, "Usuário não encontrado.\nDigite:\n1 -> para retornar ao menu\n2 -> para adicionar um novo usuário.", "Alteração de dados de usuario", JOptionPane.INFORMATION_MESSAGE);
                if("1".equals(dados)) startCrud(1);
                if("2".equals(dados)) startCrud(0);
            } else if (consultarUsuario(dados) != null) {
                try{
                String novoUsuário = JOptionPane.showInputDialog(null, "Digite os dados do novo usuário",
                        "Alteração de dados de usuario", JOptionPane.INFORMATION_MESSAGE);
                tratarDadosECadastrarUsuario(novoUsuário);
                } catch (Exception e) {
                    e.printStackTrace();
                    excluirUsuario(dados);
                }
            }
        } else if(opcao.equals("5")){
            //sair
            System.exit(0);
        } else if(opcao.equals("6")) {
            JOptionPane.showMessageDialog(null,
                    "Lista dos usuários cadastrados: \n" + consultarTodos(),
                    "Usuários cadastrados",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
