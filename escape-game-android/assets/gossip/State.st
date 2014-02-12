EVAL Class addNewClass: (Object subclass: 'State' variables: 'name onEnterBlock onExitBlock onClickBlock' classVariables: '')
METHOD State
name
    ^ name.
!
METHOD State
name: aString
    name := aString.
!
METHOD State
onEnter: aBlock
    onEnterBlock := aBlock.
!
METHOD State
onExit: aBlock
    onExitBlock := aBlock.
!
METHOD State
onClick: aBlock
    onClickBlock := aBlock.
!
METHOD State
enter
	onEnterBlock notNil ifTrue: [
        onEnterBlock value. ].
!
METHOD State
exit
    onExitBlock notNil ifTrue: [
        onExitBlock value. ].
!
METHOD State
clicked
    onClickBlock notNil ifTrue: [
        onClickBlock value. ].
!
METHOD State
clickedWith: anItemName
    onClickBlock value: anItemName.
!
