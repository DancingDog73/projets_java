import pygame
from config import*
import random
import math

pygame.init()

Herb_total = HERBS_COUNT

class Element(pygame.sprite.Sprite):
    __ids_count =0
    __group =  pygame.sprite.Group() #groupe des sprites présents sur la planète
    __morts = pygame.sprite.Group() #groupe des sprites morts au cours de la simulation
    
    @classmethod
    def decr_ids_count(cls):
        cls.__ids_count -= 1
    
    @classmethod
    def update_ressources(cls):  #ajoute de l'herbe sur la planète 
        global Herb_total
        for _ in range(HERBS_COUNT):
            global Herb_total
            Element.add_element(Herb())
        Herb_total += HERBS_COUNT

    @classmethod
    def start_simulation(cls): #lance la simulation en  ajoute les animaux  et les ressources
        [Element.add_element(Cow()) for _ in range(COWS_COUNT)]
        [Element.add_element(Lion()) for _ in range(LIONS_COUNT)]
        [Element.add_element(Dragon()) for _ in range(DRAGONS_COUNT)]
        [Element.add_element(Elephant()) for _ in range(ELEPHANTS_COUNT)]
        [Element.add_element(Hare()) for _ in range(HARES_COUNT)]
        [Element.add_element(Monkey()) for _ in range(MONKEYS_COUNT)]
        [Element.add_element(Wolf()) for _ in range(WOLFS_COUNT)]
        [Element.add_element(Giraffe()) for _ in range(GIRAFFES_COUNT)]
        [Element.add_element(Tiger()) for _ in range(TIGERS_COUNT)]

        [Element.add_element(Water()) for _ in range(WATERS_COUNT)]
        [Element.add_element(Herb()) for _ in range(HERBS_COUNT)]
        [Element.add_element(Power_up()) for _ in range(POWERUP_COUNT)]
        [Element.add_element(Trap()) for _ in range(TRAP_COUNT)]


    @classmethod
    def bilan(cls,temps,liste):
        dic = {}
        animaux = {}
        animals = 0
        global Herb_total
        print("La simulation est terminée")
        
        
        
        if len(liste)==0:
            print("La simulation s'est arrêtée car tous les animaux sont morts ")
        
        elif len(liste)==1:
            print("La simulation s'est arrêtée et ",liste[0],"a gagné")
            Element.__morts.add(liste[0])
        else:
            for elt in liste:
                Element.__morts.add(elt)
        
        for elt in cls.__morts:  #ici, on créé un dictionnaire dans lequel on regroupe tous les sprites selon leur classe
            dic[elt.get_natural_name()] = dic[elt.get_natural_name()] + 1 if elt.get_natural_name() in dic.keys()  else 1
            if issubclass(elt.__class__, Animal):
                animaux[elt.get_natural_name()] = (animaux[elt.get_natural_name()][0] + 1,elt.get_char_repr()) if elt.get_natural_name() in animaux.keys()  else (1,elt.get_char_repr())
                animals += 1

        print("Au cours de la simulation, il y a eu :")
        for key,it in animaux.items(): #on affiche les animaux qui étaient présents sur la planète 
            print(it[0] , key, it[1])

        print("Elle a duré ",temps/1000," secondes")
        
        #Ensuite on calcule la proportion de ressources consommées
        if 'Power_up' not in dic.keys() and 'Herb' not in dic.keys():
            print("Aucune ressource n'a été consommé")
        
        else :
            if 'Power_up' in dic.keys() and 'Herb' in dic.keys():
                if dic['Herb'] + dic['Power_up'] == Herb_total + POWERUP_COUNT:
                    print("Toutes les ressources ont été consommées")
                else :
                    pourcentage = round((dic['Herb'] + dic['Power_up'])/(Herb_total + POWERUP_COUNT) * 100,1)
                    print(pourcentage,"pourcents des ressources ont été consommées ")
            
            elif 'Herb' not in dic.keys():
                pourcentage = round(((dic['Power_up'])/(Herb_total + POWERUP_COUNT)) * 100,1)
                print(pourcentage,"pourcents des ressources ont été consommées ")
            else:
                pourcentage = round(((dic['Herb'])/(Herb_total) + POWERUP_COUNT) * 100,1)
                print(pourcentage,"pourcents des ressources ont été consommées ")

        print("Le climat de la planète était ",climat)
        


    @classmethod
    def incr_ids_count(cls):
        cls.__ids_count += 1
    
    @classmethod
    def remove_elt(cls,elt):
        cls.__morts.add(elt)
        cls.__group.remove(elt)

    @classmethod
    def reset(cls):
        cls.__ids_count = 0
        cls.__group =  pygame.sprite.Group()
    
    @classmethod
    def get_group(cls):
        return cls.__group

    @classmethod
    def get_group_count(cls):
        return len(cls.__group)
    
    @classmethod
    def add_element(cls,element):
        cls.__group.add(element)


    def __init__(self,name, char_repr,):
        super().__init__()
        Element.incr_ids_count()
        self.__id= Element.get_group_count()
        self.name = name
        self.__char_repr= char_repr

        img= pygame.image.load('img/'+self.get_natural_name().lower()+'.png').convert_alpha()

        self.image= pygame.transform.scale(img, (PLANET_LATITUDE_CELLS_COUNT, PLANET_LONGITUDE_CELLS_COUNT))
        self.rect = self.image.get_rect()
        self.rect.x = random.randint(0,LONGUEUR-50)
        self.rect.y = random.randint(0,LARGEUR-50)

    
    def get_id(self):
        return self.__id

    def get_char_repr(self):
        return self.__char_repr

    def get_image(self):
        return self.image
    
    def get_rect(self):
        return self.rect

    def set_rect(self, top_left_x, top_left_y):
        self.rect= pygame.Rect(top_left_x, top_left_y,self.image.get_width(), self.image.get_height())

    def get_natural_name(self):
        return self.__class__.__name__
    
    def __repr__(self):
        return f"{self.__char_repr} : {self.get_natural_name()} n°- {self.get_id()}"

      


class Animal(Element):
    def __init__(self,name,char_repr,life_max,speed_max,strength_max,is_predator,age_max):
        Element.__init__(self,name, char_repr)
        self.__age = 0
        self.__gender = random.randint(0,2)
        self.__bar_life = [life_max,life_max]
        self.__current_direction = (random.choice([0,1,-1]),random.choice([0,1,-1]))
        self.__speed_bar = [speed_max,speed_max]
        self.__strength_bar = [strength_max,strength_max]
        self.__sickness = 0
        self.__coordinates = self.get_rect()
        self.__motion = random.choice([True])
        self.__age_max = age_max
        self.target = None
        self.is_predator = is_predator
        self.assailant = None
        self.reproduction=0

    
    
    #recuperation des autres sprites
    def get_others(self,cl):
        group = pygame.sprite.Group()
        #on parcourt le groupe de sprite __group et on sélectionne ceux qui nous intéressent
        if cl == 'all':
            for elt in Element.get_group():
                if elt != self:
                    group.add(elt)
        elif cl == 'Animal':
            for elt in Element.get_group():
                if issubclass(elt.__class__, Animal) and elt != self:
                    group.add(elt)
        else :
            for elt in Element.get_group():
                if elt.__class__.__name__ == cl and elt != self:
                    group.add(elt)
        return group
    
    def get_predators(self): #on récupère tous les présateurs de la planète
        group = pygame.sprite.Group()
        for elt in Element.get_group():
            if issubclass(elt.__class__, Animal):
                if elt.is_predator == 1 and elt != self and elt.__gender == 1:
                    group.add(elt)
        return group
    
    def most_close(self,elements):
        #calcule les distances  entre un sprite et un autre groupe de sprite et renvoie le sprite le plus proche du group
        x1 = self.rect.x
        y1 = self.rect.y
        if len(elements) == 0:
            return None
        for elt in elements:
            e = elt
            break
        x2 = e.rect.x
        y2 = e.rect.y
        dist = math.sqrt((x1-x2)**2 + (y1-y2)**2)

        for elt in elements:
            x2 = elt.rect.x
            y2 = elt.rect.y
            if math.sqrt((x1-x2)**2 + (y1-y2)**2) < dist:
                dist = math.sqrt((x1-x2)**2 + (y1-y2)**2)
                e = elt
        return e
    
    def prey(self):
        #renvoie la proie la plus proche d'un prédateur
        elements = pygame.sprite.Group()
        for elt in self.get_others('Cow'):
            elements.add(elt)
        for elt in self.get_others('Hare'):
            elements.add(elt)
        for elt in self.get_others('Monkey'):
            elements.add(elt)
        return self.most_close(elements)


    def get_partner(self):
    #retourne le partenaire de reproduction le plus proche
        liste = self.get_others(self.__class__.__name__)
        gender = self.get_gender()
        opp= []
        for elt in liste:
            if elt.get_gender()!= gender:
                opp.append(elt)
        return opp

    def get_most_close_partner(self):
        elements=self.get_partner()
        return self.most_close(elements)

        
    #get et set
    def get_motion(self):
        return self.__motion

    def set_motion(self,value):
        self.__motion = value

    def get_age(self):
        return self.__age
    
    def get_age_max(self):
        return self.__age_max
    
    def ageing(self):
        #les animaux perdent en vitesse et en force en vieillissant
        self.__age += 1
        self.losing_life(0.5)
        self.decr_strength(1)
        self.decr_speed(0.5)

    
    def get_gender(self):
        return self.__gender
    
    def get_life_max(self):
        return self.__bar_life[1]

    def get_life(self):
        return self.__bar_life[0]

    def get_speed(self):
        return self.__speed_bar[0]
    
    def get_strength(self):
        return self.__strength_bar[0]
    
    def get_coordinates(self):
        return self.__coordinates
    
    def set_sickness(self,value):
        self.__sickness = 0 if value==0 else 1
    
    def get_current_direction(self):
        return self.__current_direction
    
    def set_current_direction(self,line_direction, column_direction):
        self.__current_direction = [line_direction,column_direction]
    
    def change_direction(self):
        self.__current_direction = (random.choice([0,1,-1]),random.choice([0,1,-1]))
    

    #tests
    def is_sick(self):
        return self.__sickness == 1
    
    def check_collision(self, group):
        return pygame.sprite.spritecollide(self, group, False)

    def is_alive(self):
        return self.__bar_life[0] > 0
    
    def is_dead(self):
        return self.__bar_life[0] <= 0

    
    
    #modificateurs
    def incr_speed(self,value):
        self.__speed_bar[0] = self.__speed_bar[0] + value if self.__speed_bar[0] + value <= self.__speed_bar[1] else self.__speed_bar[1]


    def decr_speed(self,value):
        self.__speed_bar[0] = self.__speed_bar[0] - value if self.__speed_bar[0] - value >= 1 else 1


    def incr_strength(self,value):
        self.__strength_bar[0] = self.__strength_bar[0] + value if self.__strength_bar[0] + value <= self.__strength_bar[1] else self.__strength_bar[1]

    def decr_strength(self,value):
        self.__strength_bar[0] = self.__strength_bar[0] - value if self.__strength_bar[0] - value >= 0 else 0

    def life_bar_update(self):
        if self.__sickness == 1:
            self.losing_life(1)
    
    def losing_life(self,value):
        self.__bar_life[0] -= value if self.__bar_life[0] - value >= 0 else 0
    
    def recovering_life(self,value):
        self.__bar_life[0] = self.__bar_life[0] + value if self.__bar_life[0] + value <= self.__bar_life[1] else self.__bar_life[1]

    #fonctions_biologiques
    def eat(self,food):
        c = food.__class__.__name__
        if c == 'Power_up':
            if random.choice([0,1]) == 0:
                self.incr_speed(2)
            else :
                self.incr_strength(2)
            self.recovering_life(1)
        else:
            self.recovering_life(3)
        self.target = None
        Element.remove_elt(food)
    
    def drink(self):
        self.recovering_life(5)
        self.incr_speed(1)
        self.target = None
        self.change_direction()
    

    def attack(self,target):
        #un prédateur attaque une proie, s'il a plus de force il mange la proie sinon la proie perd de la vie
        if self.__strength_bar[0] > target.__strength_bar[0]:
            print(target,"a été dévoré par ",self)
            self.eat(target)
        else :
            target.losing_life(self.__strength_bar[0])
            if target.__bar_life[0] == 0:
                print(target,"a été dévoré par ",self)
                self.eat(target)

            
    
    def reproduce(self): #FONCTION DE REPRODUCTION
        c = self.__class__
        if self.target.reproduction == 0:
            child = c()
            child.rect.x = self.rect.x + random.choice([PLANET_CELL_PIXEL_SIZE,-PLANET_CELL_PIXEL_SIZE])
            child.rect.y = self.rect.y + random.choice([PLANET_CELL_PIXEL_SIZE,-PLANET_CELL_PIXEL_SIZE]) 
            Element.add_element(child)
        self.target.reproduction = 1
        self.reproduction = 1
        self.target = None
        self.change_direction()
        

        
    
    
    def actions(self):
        if self.check_collision(self.get_others('Water')) and self.__bar_life[0] < self.__bar_life[1]: 
            self.drink()
        
        if self.__bar_life[0] < 2:
            self.target = self.most_close(self.get_others('Water'))
        elif self.__strength_bar[0] < self.__strength_bar[1]/2 and self.is_predator == 1:
            self.target = self.prey()
            if self.target != None:
                self.target.assailant = self
        elif self.__strength_bar[0] < 1 and self.is_predator == 0:
            self.target = self.most_close(self.get_others('Herb'))
    

        if self.target and self.target.__class__.__name__ != 'Water' and self.target.__class__.__name__ != self.__class__.__name__:
            if self.is_predator == 1 and self.check_collision([self.target]):
                self.attack(self.target)
            if self.is_predator == 0 and self.check_collision([self.target]) :
                self.eat(self.target)
        
        if self.is_predator == 1 and self.__gender == 1:
            rival = self.most_close(self.get_predators())
            if rival != None:
                if self.check_collision([rival]):
                    self.attack(rival)   

        
        if len(self.get_others('Power_up')) != 0:
            if  self.check_collision([self.most_close(self.get_others('Power_up'))]):
                self.eat(self.most_close(self.get_others('Power_up')))
        
        if self.__bar_life[0] == 0:
            print(self,"est mort par manque de ressources")
            Element.remove_elt(self)
        if self.__age >= self.__age_max:
            print(self,"est mort de vieillesse")
            Element.remove_elt(self)

        if self.__bar_life[0] > 2 and self.reproduction == 0 and self.__age > 3:
            liste= self.get_partner()
            self.target=self.get_most_close_partner()
            if self.check_collision(liste):
                self.reproduce()
        
        


    def move(self):

        l1 = PLANET_CELL_PIXEL_SIZE * PLANET_LATITUDE_CELLS_COUNT
        l2 = PLANET_CELL_PIXEL_SIZE * PLANET_LONGITUDE_CELLS_COUNT
        dir = self.__current_direction
        if self.target:
            #si target existe, l'animal essaie de s'en rapprocher
            x = self.target.rect.x
            y = self.target.rect.y
            d1 = self.rect.x - x
            d2 = self.rect.y - y
            if d1 > 0:
                while self.rect.x != x:
                    self.rect.x -= 1
                    break
            else :
                while self.rect.x != x:
                    self.rect.x += 1
                    break

            if d2 > 0:
                while self.rect.y != y:
                    self.rect.y -= 1
                    break
            else :
                while self.rect.y != y:
                    self.rect.y += 1
                    break
        
        elif self.assailant:
            #sinon si l'animal est poursuivi par un prédateur, il essaie au maximum de s'en éloigner
            x1 = self.assailant.rect.x
            y1 = self.assailant.rect.y
            x2 = self.rect.x 
            y2 = self.rect.y

            while math.sqrt((x1-x2)**2 + (y1-y2)**2) < 50:
                if x1<x2 and self.rect.x + self.__bar_life[0] < LONGUEUR - 50:
                    self.rect.x += self.__speed_bar[0]
                break
                
                if x1>x2 and self.rect.x - self.__bar_life[0] > 0:
                    self.rect.x -= self.__speed_bar[0]
                break
                
                if y1<y2 and (self.rect.y + self.__speed_bar[0]) < LARGEUR - 50 :
                    self.rect.y += self.__speed_bar[0]
                break

                if y1>y2 and (self.rect.y - self.__speed_bar[0]) > 0:
                    self.rect.y -= self.__speed_bar[0]
                break
    
        elif self.__motion  :
            #sinon il se déplace selon sa direction
            if dir[0] == 1 and (self.rect.y + self.__speed_bar[0]) < LARGEUR - 50 :
                self.rect.y += self.__speed_bar[0]
            elif dir[0] == -1  and (self.rect.y - self.__speed_bar[0]) > 0 :
                self.rect.y -= self.__speed_bar[0]
            
            if dir[1] == 1 and  self.rect.x + self.__bar_life[0] < LONGUEUR - 50:
                self.rect.x += self.__speed_bar[0]
            elif dir[1] == -1  and self.rect.x - self.__bar_life[0] > 0:
                self.rect.x -= self.__speed_bar[0]

    


    def update_health_bar(self,surface): #dessine la barre de vie des animaux
        bar_color = (111,210,46)
        back_bar_color = (60,63,60)

        bar_position = [self.rect.x+5, self.rect.y-5, (self.__bar_life[0]/self.__bar_life[1])* PLANET_CELL_PIXEL_SIZE, 5]
        back_bar_position = [self.rect.x+5, self.rect.y-5, PLANET_CELL_PIXEL_SIZE, 5]

        pygame.draw.rect(surface,back_bar_color,back_bar_position)
        pygame.draw.rect(surface, bar_color, bar_position)

    
    

    def __repr__(self):
        genre = "femelle" if self.__gender == 0 else "mâle"
        return f"{Element.__repr__(self)} ({genre} de {self.__age} ans(s)) \n - Barre de vie : {self.__bar_life[0]}/{self.__bar_life[1]}"


class Trap(Element):

    def __init__(self):

        Element.__init__(self,"Trap",'\U0001F4A3')


    def collision(self):
        collision = pygame.sprite.spritecollide(self, Element.get_group(), False, collided=pygame.sprite.collide_mask)
        for sprite in collision:
            if issubclass(sprite.__class__, Animal):
                print(sprite, "est mort à cause d'un piège")
                Element.remove_elt(sprite)

    

    def check_collision(self, group):
        return pygame.sprite.spritecollide(self, group, False)



class Ground(Element):
    def __init__(self):
        Element.__init__(self,"Ground",".")
    
    def __repr__(self):
        return f"{Element.__repr__(self)}"

class Ressource(Element):
    def __init__(self, name, char_repr ,value):
        Element.__init__(self,name, char_repr)
        self.__value = value
    
    def get_value(self):
        return self.__value
    
    def __repr__(self):
        return f"{Element.__repr__(self)} ({self.__value})"
    

class Herb(Ressource):
    def __init__(self):
        Ressource.__init__(self,'Herb',"\U0001F33F",1)
    
    

class Power_up(Ressource):
    def __init__(self):
        Ressource.__init__(self,'Power_up',"\U000026A1",1)


class Water(Ressource):
    def __init__(self):
        Ressource.__init__(self,'Water',"\U0001F41F",2)
    

class Hare(Animal):
    def __init__(self):
        Animal.__init__(self,'Hare',"\U0001F430",7,3,5,0,random.randint(7,10))

    
class Dragon(Animal):
    def __init__(self):
        Animal.__init__(self,'Dragon',"\U0001F432",20,3,10,1,random.randint(30,40))

class Lion(Animal):
    def __init__(self):
        Animal.__init__(self,'Lion',"\U0001F981",10,3,10,1,random.randint(20,30))

class Cow(Animal):
    def __init__(self):
        Animal.__init__(self,'Cow',"\U0001F42E",5,2,3,0,random.randint(15,20))

class Monkey(Animal):
    def __init__(self):
        Animal.__init__(self,'Monkey','\U0001F435',5,2,5,0,random.randint(7,10))


class Wolf(Animal):
    def __init__(self):
        Animal.__init__(self,'Wolf','\U0001F43A',5,3,5,1,random.randint(10,20))

class Giraffe(Animal):
    def __init__(self):
        Animal.__init__(self,'Giraffe','\U0001F992',5,2,5,0,random.randint(7,10))

class Elephant(Animal):
    def __init__(self):
        Animal.__init__(self,'Elephant','\U0001F418',12,2,5,0,random.randint(15,25))

class Tiger(Animal):
    def __init__(self):
        Animal.__init__(self,'Tiger','\U0001F42F',10,3,10,1,random.randint(15,25))


class Button():
    def __init__(self,x,y,path,scale):
        self.converted= pygame.image.load(path).convert_alpha()
        width = self.converted.get_width()
        height = self.converted.get_height()
        self.image=pygame.transform.scale(self.converted,(int(width*scale),int(height*scale)))
        self.rect=self.image.get_rect()
        self.rect.x=x
        self.rect.y=y
        self.clicked=False

    def draw(self,surface):
        action=False

        pos=pygame.mouse.get_pos()

        if self.rect.collidepoint(pos):
            if pygame.mouse.get_pressed()[0] == 1 and self.clicked==False:
                self.clicked=True
                action=True

        if pygame.mouse.get_pressed()[0]==0:
            self.clicked=False

        surface.blit(self.image,(self.rect.x,self.rect.y))

        return action




