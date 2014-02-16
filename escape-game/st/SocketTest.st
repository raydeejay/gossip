Socket new.
self connectTo: (NetNameResolver localHostAddress) port: 1978
self sendSomeData: ('Hello!!!!!!!!', String cr)
self close