//This class controls runtime compilation and pushing of planned operations to looper
//This is far superior then cyclic delayer concept in terms of performance if cycletime is BIG
//Author: Semion "Itaros" Nadezhdin

//TODO: need a way to handle deletions and a way to handle empty spaces

EVAL Class addNewClass: (Object subclass: 'Schedule' variables: 'javaObject cycles block isRegistered' classVariables: 'list isInit order')


META Schedule
init
	isInit ifTrue: [isInit := true. list := List new. order := 1.].
!

EVAL Schedule init.

META Schedule
setCycling: aCycles do: aBlock
	| instance |
	instance := self new.
	instance setCycles: aCycles.
	instance setBlock: aBlock.
	instance setIsRegistered: false.
	instance register.
	instance initializeCUH.
	^instance.
!

METHOD Schedule
initializeCUH
	javaObject := (System instantiateJavaClass: 'ru.sg_studio.escapegame.eventSystem.CycleUpdateHandler').
	<151 javaObject>.
!

META Schedule
incrementOrder
	order := (order+1).
!

META Schedule
call: aId
	|iBlock iSchedule|
	iSchedule := list at: aId.
	^iSchedule eval.
!

METHOD Schedule
setCycles: aCycles
	cycles :=aCycles.
!
METHOD Schedule
setIsRegistered: aIsRegistered
	isRegistered :=aIsRegistered.
!
METHOD Schedule
setBlock: aBlock
	block := aBlock.
!
METHOD Schedule
eval
	^block value.
!
METHOD Schedule
getIsRegistered
	^isRegistered.
!

METHOD Schedule
check
	isRegistered 
		ifFalse: [self register.].
!

METHOD Schedule
register
	list insert:self at:order .
	isRegistered := true.
	Schedule incrementOrder.
!