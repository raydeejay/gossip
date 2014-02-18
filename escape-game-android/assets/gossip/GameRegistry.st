// This little hack allows us to refer to GameRegistry class even when its
// methods can't be defined yet, because they reference all the other classes
//EVAL Class addNewClass: (Object subclass: 'GameRegistry' variables: 'reactors rooms inventory currentRoom javaObject' classVariables: 'instance javaObject')

META GameRegistry
instance
    instance isNil ifTrue: [ instance := self new ].
    ^ instance
!
METHOD GameRegistry
initialize
    super initialize.
    reactors := Dictionary new.
    rooms := Dictionary new.
    inventory := Inventory forJavaObject: (self javaObject invoke: 'getInventory').
!
METHOD GameRegistry
javaObject
    javaObject isNil ifTrue: [ javaObject := self class javaObject ].
    ^ javaObject
!
METHOD GameRegistry
rooms
    ^ rooms
!
METHOD GameRegistry
reactors
    ^ reactors
!
METHOD GameRegistry
inventory
    ^ inventory
!

META GameRegistry
javaObject
	javaObject isNil ifTrue: [ javaObject := (System getJavaClass: 'net.raydeejay.escapegame.GameRegistry') invokeStatic: 'instance' ].
	^ javaObject. 
!
META GameRegistry
newReactor: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((Reactor forJavaObject: aReactor) name: aString) register.
!
META GameRegistry
newRoom: aString
	| aRoom |
	aRoom := (self javaObject invoke: 'newRoom' with: aString asJavaObject).
	^ ((Room forJavaObject: aRoom) name: aString) register.
!
META GameRegistry
newDoor: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((Door forJavaObject: aReactor) name: aString) register.
!
META GameRegistry
newObtainable: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((Obtainable forJavaObject: aReactor) name: aString) register.
!
META GameRegistry
newDropper: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((Dropper forJavaObject: aReactor) name: aString) register.
!
META GameRegistry
newItemFrom: aReactor
    | anItem anItemJO aString |

    aString := (aReactor name + 'Item').
    anItemJO := (aReactor javaObject invoke: 'asItem').

"    anItem := Item new javaObjectKeepListener: anItemJO."
    anItem := Item forJavaObject: anItemJO.
    anItem name: aString.
    anItem setImage: aReactor getImage.
    anItem register.
    ^ anItem
!
META GameRegistry
newHudElement: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((HudElement forJavaObject: aReactor) name: aString) register.
!
META GameRegistry
switchToRoom: aString
    self javaObject invoke: #switchToRoom with: aString asJavaObject.
!

METHOD GameRegistry
registerReactor: aReactor
    reactors at: aReactor name put: aReactor.
!
METHOD GameRegistry
registerRoom: aRoom
    rooms at: aRoom name put: aRoom.
!

METHOD GameRegistry
getReactor: aString
	^ reactors at: aString ifAbsent: [ nil ].
!
METHOD GameRegistry
getRoom: aString
	^ rooms at: aString ifAbsent: [ nil ].
!
METHOD GameRegistry
getInventory
	^ inventory.
!
METHOD GameRegistry
currentRoom
	^ currentRoom
!
METHOD GameRegistry
switchToRoom: aString
    | room |
    
    room := self getRoom: aString.
    currentRoom := room.
    
    self javaObject invoke: #switchToRoom with: room name asJavaObject.
!

METHOD GameRegistry
getSelectedItem
	| anItem |
	anItem := javaObject invoke: #getSelectedItem.
	anItem printString = 'JavaObject<null>'
	    ifTrue: [ ^ nil ]
	    ifFalse: [ 
	        ^ (Item new javaObjectKeepListener: anItem) name: (anItem invoke: #getName) asSmallString ].
!
METHOD GameRegistry
setSelectedItem: anItem
    self javaObject invoke: #setSelectedItem with: anItem javaObject.
!
METHOD GameRegistry
clearSelectedItem
    self javaObject invoke: #clearSelectedItem.
!
METHOD GameRegistry
addToInventory: anItem
    anItem room: #SpecialInventoryRoom.
    self javaObject invoke: #addToInventory with: anItem javaObject.
"    inventory add: anItem."
!
METHOD GameRegistry
removeItem: aJavaObject
    self javaObject invoke: #removeItem with: aJavaObject.
"    inventory remove: aJavaObject."
!

METHOD GameRegistry
winGame
    self javaObject invoke: #winGame.
!
