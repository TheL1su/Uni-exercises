-module(serv).
-export([start/3, serwer/1, dodawacz/3, mnoznik/3, pytacz/2]).

serwer(S) ->
   receive
      {dodaj, N} -> W = S + N;
      {pomnoz, N} -> W = S * N;
      {wynik, PID} -> PID ! S, W = S
   end,
   serwer(W).

dodawacz(0, _, _) ->
   done;
dodawacz(N, S, PID) ->
   PID ! {dodaj, S},
   dodawacz(N-1, S+1, PID).

mnoznik(0, _, _) ->
   done;
mnoznik(N, S, PID) ->
   PID ! {pomnoz, S},
   mnoznik(N-1, S+1, PID).

pytacz(0, _) ->
   done;
pytacz(N, PID) ->
   PID ! {wynik, self()},
   receive
      L -> io:format("Otrzymana liczba: ~w~n", [L])
   end,
   pytacz(N-1, PID).

start(X1, X2, X3) ->
   PID = spawn(z2, serwer, [0]),
   spawn(z2, dodawacz, [X1, 1, PID]),
   spawn(z2, mnoznik, [X2, 1, PID]),
   spawn(z2, pytacz, [X3, PID]),
   done.