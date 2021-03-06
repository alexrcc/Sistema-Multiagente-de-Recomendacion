

# Tesis Doctoral - UPV/EHU
# C�lculo de la significancia de r mediante el estad�stico-t y la funcion cor.test()

# generamos los vectores Nivel de confianza y Nota para CVA-1
nivelC <- c(0.80,0.80,1.00,0.80,0.80,0.80,1.00,1.00,0.80,1.00,0.80,0.80,1.00,0.80,0.80,0.80,0.80,0.80,0.80,0.80,0.80,0.80,0.80,0.80,1.00,0.80,0.80,0.80,0.80)
nota <- c(3,5,5,4,4,5,4,5,3,4,4,3,4,4,3,3,3,4,4,3,4,4,3,4,4,4,5,4,3)

# generamos los vectores Nivel de confianza y Nota para CVA-2
#nivelC <- c(0.80,0.80,0.80,0.80,0.80,0.60,0.80,0.80,0.80,1.00,0.80,0.80,0.60,0.80,1.00,0.80,0.60,1.00,1.00)
#nota <- c(4,3,4,5,4,4,4,4,3,5,4,4,4,3,4,4,4,5,5)


# colocamos el n�mero de la muestra 

# CVA-1
n <- 29

# CVA-2
# n <- 19


# C�lculo manual de la significancia de r

# C�lculo de r
#r <- cor(nivelC,nota)
r <- 0.353461410076445

# C�lculo del estad�stico-t
tr <- r*(sqrt((n-2)))/sqrt((1-r**2))
cat("valor del estad�stico-t: ", tr)

# C�lculo de la p del estad�stico
ptr <- 2*pt(tr, n-2, lower.tail = FALSE)
cat("valor de la p para el estad�stico-t: ", ptr)

# C�lculo autom�tico con la funci�n de R cor.test()
# generamos los vectores Nivel de confianza y Nota para CVA-2
#nivelC <- c(0.51,0.90,0.75,0.55,0.65,0.60,0.80)
#nota <- c(7,9,8,8,8,9,10)
(cor.test(nivelC, nota))

