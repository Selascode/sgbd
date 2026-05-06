# Les dossiers
SOURCEDIR    = src
CLASSDIR     = classes
SOURCETESTDIR= srctest
CLASSTESTDIR = classestest
LIBDIR       = lib     
DOCDIR       = doc

# Outils java
JAVAC    = javac
JAVA     = java
JAVADOC  = javadoc
JAR      = jar

# Librairies
JUNIT = /usr/share/java/junit4.jar:/usr/share/java/hamcrest-all.jar

# Flags
JFLAGS     = -d $(CLASSDIR) -sourcepath $(SOURCEDIR)
JFLAGSTEST = -d $(CLASSTESTDIR) \
             -sourcepath $(SOURCETESTDIR) \
             -classpath $(CLASSDIR):$(CLASSTESTDIR):$(JUNIT)


# Fichiers sources
SOURCES := $(shell find $(SOURCEDIR) -name "*.java")
SOURCES_TEST := $(shell find $(SOURCETESTDIR) -name "*.java")

# Classes de test
TEST_CLASS ?= fr.insarouen.iti.prog.sgbd.AllTests


.PHONY: all comp comp-test test doc clean help

# --- Dossiers ---
$(CLASSDIR):
	mkdir -p $(CLASSDIR)

$(CLASSTESTDIR):
	mkdir -p $(CLASSTESTDIR)

# --- Compilation ---
all: comp 

comp: $(CLASSDIR)
	$(JAVAC) $(JFLAGS) $(SOURCES)

comp-test: $(CLASSTESTDIR) comp
	$(JAVAC) $(JFLAGSTEST) $(SOURCES_TEST)

# --- Tests ---
test: comp-test
	@echo "Lancement du test JUnit : $(TEST_CLASS)"
	$(JAVA) -classpath $(CLASSTESTDIR):$(CLASSDIR):$(JUNIT) \
	        org.junit.runner.JUnitCore $(TEST_CLASS)

# --- Documentation ---
doc:
	@echo "Création de la Javadoc"
	@mkdir -p $(DOCDIR)
	$(JAVADOC) -d $(DOCDIR) -sourcepath $(SOURCEDIR) -subpackages fr

# --- Nettoyage ---
clean:
	@echo "Nettoyage"
	rm -rf $(CLASSDIR) $(CLASSTESTDIR) $(DOCDIR)

# --- Aide ---
help:
	@echo "Commandes disponibles :"
	@echo "  make                              : Compile le projet et les tests"
	@echo "  make comp                         : Compile le code source (src -> classes)"
	@echo "  make comp-test                    : Compile les tests (srctest -> classestest)"
	@echo "  make test                         : Lance TEST_CLASS (défaut : AllTests)"
	@echo "  make test TEST_CLASS=mon.pkg.Test : Lance une classe de test spécifique"
	@echo "  make doc                          : Génère la Javadoc"
	@echo "  make clean                        : Supprime les dossiers générés"