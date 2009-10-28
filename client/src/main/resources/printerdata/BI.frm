V00,40,N,"Anrede"
V01,40,N,"Firmenname"
V02,40,N,"Vor + Zuname"
V03,40,N,"Strasse + Hausnummer"
V04,40,N,"Postleitzahl + Ort"
V05,40,N,"Land"
V06,40,N,"Billing No."
V07,40,N,"Paketmaße + Gewicht"
V08,40,N,"Shipment No."
V09,40,N,"Betrag"
V10,40,N,"Routing Barcode"
V11,40,N,"Routing Barcode HR"
V12,40,N,"License Plate Barcode"
V13,40,N,"License Plate Barcode HR"
V14,40,N,"from1"
V15,40,N,"from2"
V16,40,N,"from3"
V17,40,N,"logo"

q811
Q1360,24
S4
D10
ZT
JF

GG10,20,"DP_LOGO"
GG580,20,"DHL_EXP"
A210,20,0,2,1,2,N,"Businesspaket International"
LO0,90,811,1

*Absenderadresse
GG440,110,V17
A20,110,0,2,1,1,N,"Von/From:"
A140,110,0,2,1,1,N,V14
A140,130,0,2,1,1,N,V15
A140,150,0,2,1,1,N,V16

*Empfängeradresse
LE10,210,30,2
LE10,210,3,30
LE765,210,30,2
LE795,212,3,30
A20,220,0,2,1,1,N,"An/To:"
A140,220,0,1,2,2,N,V00
A140,250,0,1,2,2,N,V01
A140,280,0,1,2,2,N,V02
A140,310,0,1,2,2,N,V03
A140,340,0,1,2,2,N,V04
A140,370,0,1,2,2,N,V05
LE12,450,30,2
LE10,420,3,30
LE765,450,30,2
LE795,420,3,30
*
LE0,470,811,1
*
*Produkteigenschaften
LE0,471,140,1
LE0,472,140,1
A0,475,0,4,3,3,R,"   "
GG155,473,"DE_LOGO"
A600,476,0,1,1,1,N,"Day"
A700,476,0,1,1,1,N,"Time"
LE0,550,811,1
*
*Sendungsinformationen
A20,570,0,4,1,1,N,"Billing No.:"
A250,570,0,4,1,1,N,V06
A20,600,0,2,1,1,N,"Dimension/Weight:"
A250,600,0,2,1,1,N,V07
A20,620,0,2,1,1,N,"Shipment No.:"
A250,620,0,2,1,1,N,V08
LE0,660,811,1
*
*Kundeninformationen / 2D-Barcode
*
*Handlinginformationen
A10,665,0,2,1,1,N,"Vorausverfügung bei Unzustellbarkeit / En cas de non-livraison"
A10,695,0,2,1,1,N,"Rücksendung a.d. Absender auf dem preiswertesten Weg nach 14 Tagen"
A10,725,0,2,1,1,N,"Renvoyer á l'expéditeur par la voie plus économique apres 14 jours"
LE0,751,811,1
*
*Inhaltsinformationen
*
*Routing Barcode
B30,870,0,2,5,10,200,N,V10
A120,1075,0,4,1,1,N,V11
*License Plate Barcode
B30,1115,0,1,3,1,200,N,V12
A120,1320,0,4,1,1,N,V13

