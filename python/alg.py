"""
Module to handle algorithmic commands on a stmt tree.
"""

from atom import BinOpAtom, UnaryOpAtom, VarAtom

def find(stmt, i):
    if stmt.ident == i:
        return stmt
    elif isinstance(stmt, UnaryOpAtom):
        return find(stmt.r, i)
    elif isinstance(stmt, VarAtom):
        return False
    elif isinstance(stmt, BinOpAtom):
        return find(stmt.l, i) or find(stmt.r, i)
    else:
        raise Exception("Unknown atom type")
        
        

def runCmd(stmt, cmd):
    if cmd[0] == "flip":
        flip(stmt, cmd[1])
    elif cmd[0] == "quit":
        return False
    return True

def flip(stmt, i):
    a1 = find(stmt, i)
    assert isinstance(a1, BinOpAtom)
    l = a1.l
    a1.l = a1.r
    a1.r = l
