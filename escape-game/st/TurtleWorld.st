EVAL Class addNewClass: ( Object subclass: 'Turtle' variables: 'location heading penStatus drawingImage ' classVariables: '')
METHOD Turtle
drawOn: img
   drawingImage <- img
!
METHOD Turtle
heading
   ^ heading
!
METHOD Turtle
heading: h
   heading <- h
!
METHOD Turtle
move: amount
   self moveTo: location +
      ((amount * heading sin) asInteger @
      (amount * heading cos) asInteger)
!
METHOD Turtle
moveTo: newLoc
   (penStatus and: [ drawingImage notNil] ) ifTrue:
       [ drawingImage at: location drawLine: newLoc ].
   location <- newLoc
!
METHOD Turtle
penDown
   penStatus <- true
!
METHOD Turtle
penUp
   penStatus <- false
!
METHOD Turtle
turn: amount
   heading <- heading + amount degreesToRadians
!
META Turtle
new | r |
   r <- super new.
   r penUp.
   r moveTo: 0@0.
   r heading: 0.
   ^ r
!
EVAL Class addNewClass: ( Application subclass: 'TurtleWorld' variables: 'world ' classVariables: '')
METHOD TurtleWorld
newTurtle | t |
   t <- Turtle new.
   t drawOn: world.
   ^ t
!
METHOD TurtleWorld
run   |  sam |
   sam <- self newTurtle.
   sam moveTo: 200@200.
   sam penDown.
   1 to: 100 do: [:i |
     sam move:  i. sam turn: 30. self repaint. 300 sleep ]
   
!
METHOD TurtleWorld
start
   world <- Image size: 400@400.
   self title: 'Turtle World' size: 400@400 pane: (Pane image: world)
!
