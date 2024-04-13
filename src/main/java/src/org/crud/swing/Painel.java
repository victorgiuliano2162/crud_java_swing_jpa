package src.org.crud.swing;


import src.org.crud.infra.dao.UserDAO;
import src.org.crud.models.User;

import javax.swing.*;
import java.awt.*;

public class Painel extends JPanel{

    //static final int SCREEN_WIDTH = 800;
    //static final int SCREEN_HEIGHT = 600;
    UserDAO userDAO = new UserDAO();

    public Painel(){
        //this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.blue);
        this.setFocusable(true);

        userDAO.creatMap();

        startCrud(1);
    }

    public boolean isOpcaoValida(String opcao){
        if("1".equals(opcao) || "2".equals(opcao) || "3".equals(opcao) || "4".equals(opcao) || "5".equals(opcao)) return true;
        return false;
    }

    public User tratarDados(String dados) {
        if(dados == null) startCrud(0);
        String[] dadosSeparados = dados.split(",");
        if(dadosSeparados.length < 7 ) startCrud(2);

        User user = new User(dadosSeparados[0],dadosSeparados[1],dadosSeparados[2],dadosSeparados[3],dadosSeparados[4],dadosSeparados[5],dadosSeparados[6]);
        userDAO.cadastrar(user);
        return user;
    }

    //Não está funcionando, talvez o problema esteja relacionado a tipagem do map
    public void excluirUsuário(String s) {
        if(s == null || "".equals(s)) startCrud(0);
        s.trim().replaceAll("[^\\d.]", "");
        userDAO.excluir(s);
    }

    public User consultarUsuario(String s) {
        s.trim().replaceAll("[^\\d.]", "");
        if(userDAO.obterPorID(s) == null) {
            startCrud(3);
            return null;
        }
        return userDAO.obterPorID(s);
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
            String consulta = JOptionPane.showInputDialog(null,"Usuário não encontrado no sistema","Consultar usuário\nDeseja fazer outra consultar?\n1 - Sim\n2 - Não", JOptionPane.INFORMATION_MESSAGE);
            if(consulta.equals("1")) {
                opcao = "2";
            } else if(consulta.equals("2")) {
                opcao = "5";
            }
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
            String dados = JOptionPane.showInputDialog(null, "Digite os dados do cliente separados por vírgula, na seguinte ordem: nome, cpf, telefone, endereço, número, cidade, estado", "Cadastrar usuário", JOptionPane.INFORMATION_MESSAGE);
            tratarDados(dados);
        } else if(opcao.equals("2")){
            //consultar
            String dados = JOptionPane.showInputDialog(null, "Digite o cpf do usuário", "Consultar usuario", JOptionPane.INFORMATION_MESSAGE);
            if (consultarUsuario(dados) == null) {
                JOptionPane.showMessageDialog(null, "Usuário não encontrado", "Consultar usuario", JOptionPane.INFORMATION_MESSAGE);
            } else if (consultarUsuario(dados) != null) {
                JOptionPane.showMessageDialog(null, "Usuário cadastrado", "Consultar usuario", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if(opcao.equals("3")){
            //excluir
            String dados = JOptionPane.showInputDialog(null, "Digite o cpf do usuário que deseja excluir", "Exclusão de usuario", JOptionPane.INFORMATION_MESSAGE);
            excluirUsuário(dados);
        } else if(opcao.equals("4")){
            //alterar
        } else if(opcao.equals("5")){
            //sair
            System.exit(0);
        }
    }
}
