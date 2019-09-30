

# Tesis Doctoral - UPV/EHU
# Cálculo de la significancia de r mediante el estadístico-t y la funcion cor.test()

# generamos los vectores Nivel de confianza y Nota para CVA-1
nivelC <- c(0.80,0.80,1.00,0.80,0.80,0.80,1.00,1.00,0.80,1.00,0.80,0.80,1.00,0.80,0.80,0.80,0.80,0.80,0.80,0.80,0.80,0.80,0.80,0.80,1.00,0.80,0.80,0.80,0.80)
nota <- c(3,5,5,4,4,5,4,5,3,4,4,3,4,4,3,3,3,4,4,3,4,4,3,4,4,4,5,4,3)

# generamos los vectores Nivel de confianza y Nota para CVA-2
#nivelC <- c(0.80,0.80,0.80,0.80,0.80,0.60,0.80,0.80,0.80,1.00,0.80,0.80,0.60,0.80,1.00,0.80,0.60,1.00,1.00)
#nota <- c(4,3,4,5,4,4,4,4,3,5,4,4,4,3,4,4,4,5,5)


# colocamos el número de la muestra 

# CVA-1
n <- 29

# CVA-2
# n <- 19


# Cálculo manual de la significancia de r

# Cálculo de r
#r <- cor(nivelC,nota)
r <- 0.353461410076445

# Cálculo del estadístico-t
tr <- r*(sqrt((n-2)))/sqrt((1-r**2))
cat("valor del estadístico-t: ", tr)

# Cálculo de la p del estadístico
ptr <- 2*pt(tr, n-2, lower.tail = FALSE)
cat("valor de la p para el estadístico-t: ", ptr)

# Cálculo automático con la función de R cor.test()
# generamos los vectores Nivel de confianza y Nota para CVA-2
#nivelC <- c(0.51,0.90,0.75,0.55,0.65,0.60,0.80)
#nota <- c(7,9,8,8,8,9,10)
(cor.test(nivelC, nota))

