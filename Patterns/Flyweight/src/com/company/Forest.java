package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/** GUI-лес, который рисует деревья */
public class Forest extends JFrame {
    private List<Tree> trees = new ArrayList<>();
    /** Высадить дерево */
    public void plantTree(int x, int y, String name, Color color, String otherTreeData) {
        TreeType type = TreeFactory.getTreeType(name, color, otherTreeData);
        Tree tree = new Tree(x, y, type);
        trees.add(tree);
    }
    /** Отрисовка леса (Перегрузка метода библиотеки JFrame) */
    @Override
    public void paint(Graphics graphics) {
        for (Tree tree : trees) {
            tree.draw(graphics);
        }
    }
}
