// tests with moving all the game to the Gossip side

EVAL Class addNewClass: (Object subclass: 'Room01' variables: '' classVariables: '')
META Room01
setup
    | box key |
     
    box := (GameRegistry newReactor: #box).
    box setImage: 'box.png'.
    box setX: 100 Y: 20.
    box onClick: [ box moveToInventory ].
    box addToRoomNamed: #room01.
    
    key := (GameRegistry newReactor: #key).
    key setImage: 'key.png'.
    key setX: 200 Y: 20.
    key onClick: [ key moveToInventory ].
    key addToRoomNamed: #room02.
    
!
