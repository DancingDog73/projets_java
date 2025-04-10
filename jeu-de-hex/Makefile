SRC=src
BIN=bin
MAIN=model.Main
VIEW=view.HexGameView
GAMESIMULATION=model.result.GameSimulation

JAVAC=javac
JAVA=java
PYTHON=python3
PIP=pip


FLAGS=-d $(BIN) -sourcepath $(SRC)

all: clean compile

compile:
	mkdir -p $(BIN)
	$(JAVAC) $(FLAGS) $(shell find $(SRC) -name "*.java")

run: compile
	$(JAVA) -cp $(BIN) $(MAIN)
	
run-view: compile
	$(JAVA) -cp $(BIN) $(VIEW)
	

clean:
	rm -rf $(BIN)

install-deps:
	$(PIP) install -r requirements.txt

run-visualisation:
	@if [ -z "$(word 2, $(MAKECMDGOALS))" ]; then \
		echo "Erreur : Vous devez spécifier un script à exécuter. Exemple : make run-visualisation mon_script.py"; \
		exit 1; \
	fi; \
	cd src/model/result/insights/visualisations && $(PYTHON) $(word 2, $(MAKECMDGOALS))

run-repeated-simulations:
	@if [ -z "$(word 2, $(MAKECMDGOALS))" ] || [ -z "$(word 3, $(MAKECMDGOALS))" ] || [ -z "$(word 4, $(MAKECMDGOALS))" ] || [ -z "$(word 5, $(MAKECMDGOALS))" ] || [ -z "$(word 6, $(MAKECMDGOALS))" ]; then \
		echo "Erreur : Vous devez spécifier 5 arguments : SIMULATIONS, BUDGET_TOTAL, BUDGET_PROPORTION_P1, GRID_SIZE et RAVE_PLAYER_ONE"; \
		echo "Exemple : make run-repeated-simulations 1000 50 0.6 11 true"; \
		exit 1; \
	fi; \
	$(JAVA) -cp $(BIN) $(GAMESIMULATION) repeatSameSimulation $(word 2, $(MAKECMDGOALS)) $(word 3, $(MAKECMDGOALS)) $(word 4, $(MAKECMDGOALS)) $(word 5, $(MAKECMDGOALS)) $(word 6, $(MAKECMDGOALS))


run-simulations:
	@if [ -z "$(word 2, $(MAKECMDGOALS))" ] || [ -z "$(word 3, $(MAKECMDGOALS))" ] || [ -z "$(word 4, $(MAKECMDGOALS))" ] || [ -z "$(word 5, $(MAKECMDGOALS))" ]; then \
		echo "Erreur : Vous devez spécifier 4 arguments : SIMULATIONS, BUDGET_TOTAL, BUDGET_PROPORTION_P1 et GRID_SIZE"; \
		echo "Exemple : make run-simulations 1000 50 0.6 11"; \
		exit 1; \
	fi; \
	$(JAVA) -cp $(BIN) $(GAMESIMULATION) $(word 2, $(MAKECMDGOALS)) $(word 3, $(MAKECMDGOALS)) $(word 4, $(MAKECMDGOALS)) $(word 5, $(MAKECMDGOALS))



run-simulations-with-xml:
	$(JAVA) -cp $(BIN) $(GAMESIMULATION)


	
run-correlation:
	@if [ -z "$(word 2, $(MAKECMDGOALS))" ]; then \
		echo "Erreur : Vous devez spécifier un script à exécuter. Exemple : make run-correlation mon_script.py"; \
		exit 1; \
	fi; \
	cd src/model/result/insights/correlation && $(PYTHON) $(word 2, $(MAKECMDGOALS))
	
	
run-ml:
	@if [ -z "$(word 2, $(MAKECMDGOALS))" ]; then \
		echo "Erreur : Vous devez spécifier un script à exécuter. Exemple : make run-ml mon_script.py"; \
		exit 1; \
	fi; \
	cd src/model/result/insights/ml && $(PYTHON) $(word 2, $(MAKECMDGOALS))

list-scripts:
	@echo "Scripts de visualisation :"
	@ls src/model/result/insights/visualisations/*.py 2>/dev/null | xargs -n 1 basename || echo "Aucun script trouvé."
	@echo ""
	@echo "Scripts de corrélation :"
	@ls src/model/result/insights/correlation/*.py 2>/dev/null | xargs -n 1 basename || echo "Aucun script trouvé."
	@echo ""
	@echo "Scripts de machine learning :"
	@ls src/model/result/insights/ml/*.py 2>/dev/null | xargs -n 1 basename || echo "Aucun script trouvé."
