import pygame
import draw
import sys
import select
import alg
from threading import Thread
from Queue import Empty, Queue
from atom import *
from util import Pos

cmdq = Queue()
running = True

def console_thread():
    while running:
        line = sys.stdin.readline().strip()
        tokens = line.split(" ")
        if (tokens[0] == "flip"):
            if len(tokens) < 2:
                print "Needs 2 tokens."
            else:
                i = int(tokens[1])
                cmdq.put((tokens[0], i))
        elif (tokens[0] == "quit"):
            cmdq.put((tokens[0],))

if __name__ == "__main__":
    x = VarAtom("x")
    print "x", x.ident
    y = VarAtom("y")
    print "y", y.ident
    stmt = UnaryOpAtom("\\integral", BinOpAtom("+", x, y))
    print "stmt", stmt.ident
    
    b = stmt.render()
    draw.boxes = [b]

    t = Thread(target=console_thread)
    t.start()

    # Event loop
    running = True
    while running:
        draw.draw()
        running = draw.events()
        while not cmdq.empty():
            try:
                cmd = cmdq.get_nowait()
                running = alg.runCmd(stmt, cmd)
                print stmt
                # Rerender
                b = stmt.render()
                draw.boxes = [b]
            except Empty:
                break

    # Cleanup
    t.join()
