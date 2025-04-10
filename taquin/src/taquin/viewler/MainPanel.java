package taquin.viewler;


import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import util.mvc.Listener;
import util.processors.*;
import taquin.model.*;



public class MainPanel extends JPanel implements Listener {


    private CardLayout manager;
    private NavigationModel navigator;

    
    public MainPanel () {

        super();

        this.manager = new CardLayout();
        this.navigator = new NavigationModel();

        this.init();

    }


    public void init () {

        this.navigator.addListener(this);
        this.setLayout(this.manager);
        //this.setPreferredSize(new Dimension(800,200));

        this.add(new HomePanel(this.navigator), "Home");
        this.add(new SizeChoicePanel(this.navigator), "SizeChoice");
        this.add(new SurfaceChoicePanel(this.navigator), "SurfaceChoice");
        this.add(new ImageChoicePanel(this.navigator), "ImageChoice");

        this.navigator.goTo("Home");

    }


    @Override
    public void updatedModel (Object source) {

        String currentPage = this.navigator.getCurrentPage();

        if (currentPage == "NumbersSurface") {
            List<String> numbersList = new ArrayList<>();
            for (int i=1; i <= this.navigator.getSurfaceSize()*this.navigator.getSurfaceSize(); i+=1) {
                numbersList.add(""+i);
            }
            this.add(new Surface<String>(new GameModel<String>(numbersList), this.navigator), "NumbersSurface");
        }

        if (currentPage == "ImageSurface") {
            ImageSpliter spliter = new ImageSpliter();
            this.add(new Surface<ImageIcon>(new GameModel<ImageIcon>(spliter.splitImage(((ImageChoicePanel)this.getComponent(3)).getChosenImage(), this.navigator.getSurfaceSize())), this.navigator), "ImageSurface");
        }

        this.manager.show(this, currentPage);

    }


}