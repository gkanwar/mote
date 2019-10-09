"""
Module to handle algorithmic commands on a stmt tree.
"""

from atom import *

"""
Recursively execute function f over the tree. If greedy is True, then
stop as soon as f returns some value that casts to True.
"""
def exec_rec(stmt, f, greedy):
    res = f(stmt)
    if greedy and res: return res
    
    if isinstance(stmt, UnaryOpAtom):
        return exec_rec(stmt.r, f, greedy)
    elif isinstance(stmt, VarAtom):
        return f(stmt)
    elif isinstance(stmt, BinOpAtom):
        res_l = exec_rec(stmt.l, f, greedy)
        if greedy and res_l: return res_l
        res_r = exec_rec(stmt.r, f, greedy)
        return res_r
    elif isinstance(stmt, ProdAtom):
        res_i = None
        for t in stmt.terms:
            res_i = exec_rec(t, f, greedy)
            if greedy and res_i: return res_i
        return res_i
    elif isinstance(stmt, SumAtom):
        res_i = None
        for t in stmt.terms:
            res_i = exec_rec(t, f, greedy)
            if greedy and res_i: return res_i
        return res_i
    else:
        raise Exception("Unknown atom type")
        
def find(stmt, i):
    return exec_rec(stmt, lambda t: t if (t.ident == i) else False, True)


def runCmd(stmt, cmd):
    if cmd[0] == "flip":
        flip(stmt, cmd[1])
    elif cmd[0] == "print":
        a = find(stmt, cmd[1])
        print a
    elif cmd[0] == "dist_over":
        dist_over(stmt, cmd[1], cmd[2])
    elif cmd[0] == "quit":
        return False
    return True

def flip(stmt, i):
    a1 = find(stmt, i)
    assert isinstance(a1, BinOpAtom)
    l = a1.l
    a1.l = a1.r
    a1.r = l

def dist_over(stmt, i, i_top):
    a1 = find(stmt, i)
    a_top = find(stmt, i_top)
    assert isinstance(a1, SumAtom)
    assert isinstance(a_top, ProdAtom)
    ind = a_top.terms.index(a1)
    new_terms = []
    for sub_term in a1.terms:
        a_top_clone = a_top.clone()
        a_top_clone.terms[ind] = sub_term
        new_terms.append(a_top_clone)
    a_top.terms = [SumAtom(new_terms)]
