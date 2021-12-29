# A -> McDonald's
# B -> TacoBell, KFC, Dom Franguito, Patrono Pizza
# C -> H3, Vitaminas, ALi Kebab, Italian Republic, Mania Polé Bowls
# D -> A Gula do Prego, AKi Grill, Oita, Alicarius, Bica da Ria, Caseiro e Bom, Hummy, Malguinhas e Pregos, Pão Divino, Sun Bufê, Tasquinha do Bacalhau
# E -> Sical, Zé da Tripa, Caffelato

#[9-11]; [12-15]; [16-18]; [19-23]

import random, string

restaurants = {"H3": 'C', "KFC": 'B', "McDonald's": 'A', "Taco Bell": 'B', "Vitaminas": 'C', "A Gula do Prego": 'D', "Aki Grill": 'D', "Ali Kebab": 'C', "Oita": 'D', "Dom Franguito": 'B', "Alicarius": 'C', "Bica da Ria": 'D', "Caffelato": 'E', "Caseiro e Bom": 'D', "Hummy": 'D', "Italian Republic": 'C', "Malguinhas e Pregos": 'D', "Mania Poké Bowls": 'C', "Patrono Pizza": 'B', "Pão Divino": 'D', "Sical": 'E', "Sun Bufê": 'D', "Tasquinha do Bacalhau": 'D', "Zé da Tripa": 'E'}
f = open("data_info.txt", "w")

for d in range (1, 30):
    f.write(f'Dia: {d}\n')

    for r in restaurants.keys():
        #Cada restaurante tem controlo sobre os próprios códigos, restaurantes diferentes podem ter códigos iguais, o mesmo restaurante não pode ter 'simultaneamente' 2 códigos repetidos
        code = []

        f.write(f'Restaurante: {r}\n')

        if (restaurants.get(r) == 'A'):
            orders = [[30, 40], [100, 150], [40, 90], [100, 180]]
        elif (restaurants.get(r) == 'B'):
            orders = [[20, 30], [80, 120], [20, 60], [70, 150]]
        elif (restaurants.get(r) == 'C'):
            orders = [[10, 30], [70, 110], [10, 40], [60, 130]]
        elif (restaurants.get(r) == 'D'):
            orders = [[10, 20], [50, 90], [10, 30], [50, 110]]
        else:
            orders = [[10, 40], [50, 150], [10, 60], [50, 180]]
        
        for h in range(0,4):
            orders_t = random.randint(orders[h][0], orders[h][1])
            for i in range(0, orders_t):
                c = ''.join(random.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for _ in range(8))
                while (c in code):
                    c = ''.join(random.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for _ in range(8))
            
                print(c)
                code.append(c)

                if (h == 0):
                    i_hour = random.randint(9, 11)
                elif (h == 1):
                    i_hour = random.randint(12, 15)
                elif (h == 2):
                    i_hour = random.randint(16, 18)
                else:
                    i_hour = random.randint(19, 23)

                i_min = random.randint(0, 59)
                i_sec = random.randint(0, 59)

                if (restaurants.get(r) == 'A' or restaurants.get(r) == 'B'):
                    p_time = random.randint(2,6)
                elif (restaurants.get(r) == 'C' or restaurants.get(r) == 'D'):
                    p_time = random.randint(7,20)
                else:
                    p_time = random.randint(1,5)
            
                f.write(f'\t Código: {c} \t Iniciado: {i_hour}:{i_min}:{i_sec} \t\t Tempo previsto: {p_time}\n')
f.close