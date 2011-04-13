
%.class:%.java
	javac $<

OBJ = Cli.class Dalek.class DalekFactory.class DalekSection.class \
	DalekSectionDome.class DalekSectionSkirt.class DalekTech.class \
	Hex.class Map.class Player.class Position.class Tables.class \
	UserInterface.class Weapon.class  \
	Guitwo.class selectFactoryDaleksPanel.class mapPanel.class \
	statusPanel.class Direction.class ProbabilityUI.class \
	BlackRenegadeDamage.class GreyRenegadeDamage.class  InvasionDamage.class  SpecialWeaponDamage.class \
	GoldSupremeDamage.class  ImperialDamage.class  RedSaucerDamage.class \
	MapImage.class TacticalUI.class BlueDroneTactical.class \
	BlackRenegadeTactical.class GoldSupremeTactical.class  GreyRenegadeTactical.class ImperialTactical.class \
	RedCommanderTactical.class RedSaucerTactical.class SpecialWeaponTactical.class \
	WeaponUI.class \
	Network.class NetworkClient.class 

all: ${OBJ}

jar: DalekTech.jar

DalekTech.jar: ${OBJ}
	jar cf DalekTech.jar *class Images

BlackRenegadeTactical.class: TacticalUI.java BlackRenegadeTactical.java
GreyRenegadeTactical.class: TacticalUI.java GreyRenegadeTactical.java
GoldSupremeTactical.class: TacticalUI.java GoldSupremeTactical.java
ImperialTactical.class: TacticalUI.java ImperialTactical.java
RedSaucerTactical.class: TacticalUI.java RedSaucerTactical.java
SpecialWeaponTactical.class: TacticalUI.java SpecialWeaponTactical.java

BlackRenegadeDamage.class: BlackRenegadeDamage.java DamageUI.java
GreyRenegadeDamage.class:  DamageUI.java
InvasionDamage.class: DamageUI.java
SpecialWeaponDamage.class: DamageUI.java
GoldSupremeDamage.class:  DamageUI.java
ImperialDamage.class:  DamageUI.java
RedSaucerDamage.class: DamageUI.java

