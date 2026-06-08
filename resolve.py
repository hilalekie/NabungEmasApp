import sys

def resolve_conflicts(filepath, keep_theirs=True):
    with open(filepath, 'r', encoding='utf-8') as f:
        lines = f.readlines()
        
    out = []
    state = 'NORMAL' # NORMAL, IN_OURS, IN_THEIRS
    for line in lines:
        if line.startswith('<<<<<<< HEAD'):
            state = 'IN_OURS'
            continue
        if line.startswith('======='):
            state = 'IN_THEIRS'
            continue
        if line.startswith('>>>>>>>'):
            state = 'NORMAL'
            continue
            
        if state == 'NORMAL':
            out.append(line)
        elif state == 'IN_OURS' and not keep_theirs:
            out.append(line)
        elif state == 'IN_THEIRS' and keep_theirs:
            out.append(line)
            
    with open(filepath, 'w', encoding='utf-8') as f:
        f.writelines(out)

resolve_conflicts('app/src/main/java/com/example/nabungemas/ui/screens/ProfileScreen.kt', keep_theirs=True)
resolve_conflicts('app/src/main/java/com/example/nabungemas/ui/navigation/NavGraph.kt', keep_theirs=True)
