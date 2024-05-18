package org.crud.swing;

import javax.swing.*;

public class Frame extends JFrame {

    public Frame() {
        this.add(new Painel());
        this.setTitle("Crud Java Swing");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
