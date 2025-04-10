import pygame 
from config import*
from elements import*

pygame.init()


pygame.display.set_caption("Projet")
screen = pygame.display.set_mode((LONGUEUR,LARGEUR))


#icon
banner = pygame.image.load('img/elephant.png').convert_alpha()
pygame.display.set_icon(banner)

#background
def make_background(name,longueur, largeur,surface):
    background = pygame.image.load('img/'+name).convert_alpha()
    bg_width,bg_height =background.get_size()
    background = pygame.transform.scale(background, (longueur, largeur))
    surface.blit(background,(0,0))


#bouttons de menu
play_btn=Button(450,500,'img/playy.png', 0.6)
quit_btn=Button(200,500,'img/quit.png', 0.6)
options_btn=Button(700,500,'img/options.png',0.6)


#bouttons de options
back_btn=Button(40,610,'img/back3.png', 0.35)
bsummer_btn=Button(480,120,'img/bsummer.png', 0.7)
bwinter_btn=Button(480,250,'img/bwinter.png', 0.7)
bfall_btn=Button(480,380,'img/bfall.png', 0.7)
bspring_btn=Button(480,510,'img/bspring.png', 0.7)



running = True
menu=True
options=False
start=False

Element.start_simulation()


p = pygame.sprite.Group()
time = pygame.time.get_ticks() #Durée de la simulation
time2 = pygame.time.get_ticks() #Traqueur de temps pour le renouvellement des ressources
time_reproduction = pygame.time.get_ticks() #Traqueur de temps pour la reproduction des animaux

while running: 
    
    if menu:
        #MENU
        make_background("starting.png",LONGUEUR,LARGEUR,screen)

        if play_btn.draw(screen):
            start=True
        
        elif quit_btn.draw(screen):
            running=False
        
        elif options_btn.draw(screen):
            make_background('background.png', LONGUEUR,LARGEUR,screen)
            options=True
            menu=False
            
        
            
        

    if options:

        #si l'utilisateur clique sur options, on affiche les quatres options disponibles
        if back_btn.draw(screen):
            menu=True
            options=False

        if bsummer_btn.draw(screen):
            weather='summer'
            start=True
            options=False

        elif bwinter_btn.draw(screen):
            weather='winter'
            start=True
            options=False

        elif bfall_btn.draw(screen):
            weather='fall'
            start=True
            options=False

        elif bspring_btn.draw(screen):
            weather='spring'
            start=True
            options=False
        
    #event handler
    for event in pygame.event.get():
        #quit game
        if event.type == pygame.QUIT:
            running = False
                
    pygame.display.update()
    
        
    while start: 

        make_background(weather+'.jpg', LONGUEUR,LARGEUR,screen)
        Element.get_group().draw(screen)
                    
        liste = [] 

        for elt in Element.get_group():
            if issubclass(elt.__class__, Animal):
                elt.move() #on fait bouger tous les éléments de la classe animal
                elt.actions() #Ensuite on les fait chasser, se reproduire... 
                elt.update_health_bar(screen) #Et on met à jour leur barre de santé
                liste.append(elt) #on les ajoute à liste pour vérifier s'il reste des animaux
                if pygame.time.get_ticks() - time_reproduction >= 7000:
                    elt.reproduction = 0
                    time_reproduction = pygame.time.get_ticks()

        if len(liste) <=1: #S'il reste un seul animal ou zéro, on arrête la simulation
            temps = time = pygame.time.get_ticks()
            Element.bilan(temps,liste)
            start = False
            running=False

        for elt in Element.get_group():
            if issubclass(elt.__class__, Animal):
                elt.update_health_bar(screen)

            elif issubclass(elt.__class__, Trap) and elt.check_collision:
                elt.collision()


        for elt in Element.get_group():
            if issubclass(elt.__class__, Animal):
                elt.update_health_bar(screen)

        pygame.display.flip()
        
        if pygame.time.get_ticks() - time >= 2000: #on change la direction des animaux et on les fait vieillir toutes les 2 secondes
            time = pygame.time.get_ticks()
            for elt in Element.get_group():
                if issubclass(elt.__class__, Animal):
                    elt.ageing()
                    elt.change_direction()
        
        
        if pygame.time.get_ticks() - time2 >= 15000: #Mettre à jour les ressources toutes les 15 secondes
            time2 = pygame.time.get_ticks()
            Element.update_ressources()

        pygame.display.flip()

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                start=False
                running=False
                temps = time = pygame.time.get_ticks()
                pygame.quit()
                print("La simulation a été arrêtée par l'utilisateur")
                Element.bilan(temps,liste)

pygame.quit()


        
