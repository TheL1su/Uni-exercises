-module(drzewo).
-export([start/2,tree/3]).

tree(0,X,PID)->
    io:format("~p ",[[X]]),
    PID ! {self(),[X]},
    done;
tree(N,X,PID)->
    
    Left = spawn(drzewo,tree,[N-1,2*X,self()]),
    Right = spawn(drzewo,tree,[N-1,2*X+1,self()]),
    receive {Left,Lista1} -> Z = Lista1 end,
    receive {Right,Lista2} -> Y = Lista2 end,
    io:format("~p ",[Z++Y]),
    PID ! {self(),Z ++ Y}.

start(N,X)->
    spawn(drzewo,tree,[N,X,self()]),
    receive 
        {Something,Value} -> io:format("~p", [Value])
    end.