:- use_module(library(clpfd)).
pokemon(X,Result(A,S)):-
	pokemon(X,S), 
	A \= 