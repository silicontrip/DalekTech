
%.class:%.java
	javac $<

all: Cli.class Dalek.class DalekFactory.class DalekSection.class \
	DalekSectionDome.class DalekSectionSkirt.class DalekTech.class \
	Hex.class Map.class Player.class Position.class Tables.class \
	UserInterface.class Weapon.class Network.class NetworkClient.class \
	Guitwo.class selectFactoryDaleksPanel.class mapPanel.class
