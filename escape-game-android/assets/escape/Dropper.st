EVAL Class addNewClass: (Reactor subclass: 'Dropper' variables: 'reactTo dropAtX dropAtY dropItem dropType shouldStay' classVariables: '')
METHOD Dropper
initialize
    | default selectedItem itemName newDrop |
    super initialize.
"    gr := (System getJavaClass: 'net.raydeejay.escapegame.GameRegistry') invokeStatic: 'instance'."

    shouldStay := false.
        
    default := State new.
    default name: #default.
    default onClick: [ 
        selectedItem := GameRegistry instance getSelectedItem.
    
        selectedItem isNil
            ifTrue: [ self. "Do nothing" ]
            ifFalse: [
                selectedItem name = reactTo ifTrue: [
    	            GameRegistry instance removeItem: selectedItem javaObject.
    	            
    	            newDrop := GameRegistry instance getReactor: dropItem.
    	            newDrop setX: dropAtX Y: dropAtY.
    	            newDrop addToRoomNamed: room.
    	            
    	            shouldStay ifFalse: [ self removeFromRoom. ].    	            
    	        ].
    	    ].
        ]. 
    
    self addState: default.
    self switchToState: #default.
!
METHOD Dropper
dropAtX: aX Y: aY item: aString type: aClass
    dropAtX := aX.
    dropAtY := aY.
    dropItem := aString.
    dropType := aClass.
!
METHOD Dropper
reactTo: aString
    reactTo := aString.
!
METHOD Dropper
shouldStay: aBoolean
    shouldStay := aBoolean.
!
METHOD Dropper
shouldStay
    self shouldStay: true.
!
METHOD Dropper
shouldNotStay: aBoolean
    self shouldStay: false.
!

META GameRegistry
newDropper: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((Dropper forJavaObject: aReactor) name: aString) register.
!
