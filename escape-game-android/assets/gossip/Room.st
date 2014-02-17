EVAL Class addNewClass: (JavaWrapper subclass: 'Room' variables: 'name left right top bottom' classVariables: '')
METHOD Room
initialize
    super initialize.
!
METHOD Room
background: aString
    self call: #setBackgroundFilename with: aString.
!
METHOD Room
name: aString
    name := aString.
!
METHOD Room
name
    ^ name.
!
METHOD Room
register
	GameRegistry instance registerRoom: self.
!
METHOD Room
left: aString
    left := aString.
    self call: #setExitLeft with: aString.
!
METHOD Room
left
    ^ left
!
METHOD Room
right: aString
    right := aString.
    self call: #setExitRight with: aString.
!
METHOD Room
right
    ^ right
!
METHOD Room
top: aString
    top := aString.
    self call: #setExitTop with: aString.
!
METHOD Room
top
    ^ top
!
METHOD Room
bottom: aString
    bottom := aString.
    self call: #setExitBottom with: aString.
!
METHOD Room
bottom
    ^ bottom
!
