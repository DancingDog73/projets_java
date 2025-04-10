import random
# PARAMETRES GLOBAUX de l'application (de la planète et de ses éléments)

FPS = 100                                                                                                                                    

# MONDE
# taille axe EST/OUEST de la planète
PLANET_LONGITUDE_CELLS_COUNT = 50
# taille NORD/SUD de la planète
PLANET_LATITUDE_CELLS_COUNT = 50
# taille d'un rectangle de géolocalisation de la planète
PLANET_CELL_PIXEL_SIZE = 50

LONGUEUR = 1080
LARGEUR = 720

# ANIMAUX
COWS_COUNT = 5
DRAGONS_COUNT = 2
LIONS_COUNT = 2
TIGERS_COUNT = 4
HARES_COUNT = 5
MONKEYS_COUNT=2
WOLFS_COUNT=3
GIRAFFES_COUNT=2
ELEPHANTS_COUNT=4


# PIEGE
TRAP_COUNT = 2


#CLIMAT
options_weather=['summer','winter','fall','spring']

weather=random.choice(options_weather)



# RESSOURCES
if weather == 'summer':
    WATERS_COUNT = 7
    HERBS_COUNT = 20
    POWERUP_COUNT = 7

    ELEPHANTS_COUNT=0
    MONKEYS_COUNT=0
    GIRAFFES_COUNT=4


    climat='sec'

elif weather=='winter':
    WATERS_COUNT = 15
    HERBS_COUNT = 10
    POWERUP_COUNT = 5

    COWS_COUNT=10
    WOLFS_COUNT=5


    climat='humide'

elif weather=='fall':
    WATERS_COUNT = 10
    HERBS_COUNT = 12
    POWERUP_COUNT = 6

    TIGERS_COUNT=5

    climat='peu humide'

elif weather=='spring':
    WATERS_COUNT = 12
    HERBS_COUNT = 20
    POWERUP_COUNT = 8

    HARES_COUNT= 4
    COWS_COUNT=7

    climat='peu sec'

