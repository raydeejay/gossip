EVAL Class addNewClass: (Reactor subclass: 'Dropper' variables: 'reactTo dropAtX dropAtY dropItem shouldStay' classVariables: '')
METHOD Dropper
initialize
    | default gr selectedItem itemName newDrop |
    super initialize.
    gr := (System getJavaClass: 'net.raydeejay.escapegame.GameRegistry') invokeStatic: 'instance'.

    shouldStay := false.
        
    default := State new.
    default name: #default.
    default onClick: [ 
        selectedItem := gr invoke: #getSelectedItem.
        selectedItem printString = 'JavaObject<null>'
            ifTrue: [ self. "System println: 'nil'" ]
            ifFalse: [
	        	System println: 'dropper onClick invoked once'.
                itemName := (selectedItem invoke: #getName) asSmallString.
    	        itemName = reactTo ifTrue: [
    	            gr invoke: #removeItem with: selectedItem.
    	            
    	            newDrop := Obtainable forJavaObject: (gr invoke: #getReactor with: dropItem asJavaObject).
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
dropAtX: x Y: y item: aString
    dropAtX := x.
    dropAtY := y.
    dropItem := aString.
!
METHOD Dropper
reactTo: aString
    reactTo := aString.
!
METHOD Dropper
shouldStay: aBoolean
    shouldStay := aBoolean.
!
