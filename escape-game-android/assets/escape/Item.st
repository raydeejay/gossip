EVAL Class addNewClass: (Reactor subclass: 'Item' variables: '' classVariables: '')
METHOD Item
initialize
    | default |
    super initialize.
    default := State new.
    default name: #default.
    default onClick: [ self clicked ]. 
    
    self addState: default.
    self switchToState: #default.
!
METHOD Item
addToInventory
    self.
!
METHOD Item
removeFromInventory
    self.
!
METHOD Item
beUnselected
    self call: #beUnselected.
    GameRegistry instance clearSelectedItem.
!
METHOD Item
beSelected
    self call: #beSelected.
    GameRegistry instance setSelectedItem: self.
!
METHOD Item
isSelected
    | s | 
    s := GameRegistry instance getSelectedItem.
    s isNil
        ifTrue: [ ^ false ]
        ifFalse: [ ^ s name = name ].
!
METHOD Item
clicked
    | s | 
    self isSelected
        ifTrue: [ self beUnselected ]
        ifFalse: [ 
            s := GameRegistry instance getSelectedItem.
            s isNil
                ifTrue: [ self beSelected ]
                ifFalse: [ self "combine items" ] ].
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
