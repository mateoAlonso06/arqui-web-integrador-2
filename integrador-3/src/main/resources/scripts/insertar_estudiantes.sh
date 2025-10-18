# URL del endpoint
URL="http://localhost:8080/api/v1/estudiantes"

# Archivo JSON con los datos
FILE="../jsons/estudiantes.json"

# Verificar que jq esté instalado
if ! command -v jq &> /dev/null; then
  echo "❌ Error: jq no está instalado. Ejecuta: sudo apt install jq"
  exit 1
fi

# Verificar que el archivo existe
if [ ! -f "$FILE" ]; then
  echo "❌ No se encontró el archivo $FILE"
  exit 1
fi

# Leer cantidad de elementos
TOTAL=$(jq length "$FILE")
echo "📦 Enviando $TOTAL estudiantes a $URL ..."

# Recorrer y enviar uno a uno
for ((i=0; i<TOTAL; i++)); do
  DATA=$(jq -c ".[$i]" "$FILE")
  echo "➡️  Enviando estudiantes $((i+1))/$TOTAL: $DATA"
  curl -s -X POST "$URL" \
       -H "Content-Type: application/json" \
       -d "$DATA" | jq .
done

echo "✅ Todas los estudiantes fueron enviadas correctamente."
