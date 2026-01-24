import fs from "fs"
import path from "path"

const CSV_DIR = path.resolve("csv")

if (!fs.existsSync(CSV_DIR)) {
  fs.mkdirSync(CSV_DIR)
}


// CONFIG
const QTD_USUARIOS = 100
const QTD_SETORES = 5
const QTD_ACESSOS = 10000

// Utils
function rand(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min
}

function randomTime(start = 6, end = 22) {
  const h = rand(start, end)
  const m = rand(0, 59)
  return `${String(h).padStart(2, "0")}:${String(m).padStart(2, "0")}`
}

function addMinutes(time, minAdd) {
  let [h, m] = time.split(":").map(Number)
  m += minAdd
  h += Math.floor(m / 60)
  m = m % 60
  return `${String(h).padStart(2, "0")}:${String(m).padStart(2, "0")}`
}

// ================= USERS =================
const roles = ["ADMIN","MANAGER", "EMPLOYEE" , "VISITOR" ]

const usuarios = []
for (let i = 1; i <= QTD_USUARIOS; i++) {
  usuarios.push({
    id: i,
    matricula: "MAT" + rand(100000, 999999),
    nome: `Usuario_${i}`,
    role: roles[rand(0, roles.length - 1)],
    ativo: Math.random() > 0.1 // 10% inativos
  })
}

// ================= SETORES =================
const setores = [
  "TI", "RH", "FINANCEIRO", "ALMOXARIFADO", "DIRETORIA"
].map((nome, i) => ({
  id: i + 1,
  nome
}))

// ================= PERMISSOES =================
let permId = 1
const permissoes = []

usuarios.forEach(u => {
  if (Math.random() > 0.3) { // 70% têm permissão
    const setor = setores[rand(0, setores.length - 1)]
    const inicio = randomTime(7, 10)
    const fim = randomTime(16, 22)

    permissoes.push({
      id: permId++,
      usuario_id: u.id,
      setor_id: setor.id,
      horario_inicio: inicio,
      horario_fim: fim,
      pode_entrar: true
    })
  }
})

// ================= ACESSOS (LOGS) =================
let acessoId = 1
const acessos = []

for (let i = 0; i < QTD_ACESSOS; i++) {
  const user = usuarios[rand(0, usuarios.length - 1)]
  const setor = setores[rand(0, setores.length - 1)]
  const entrada = randomTime(6, 22)
  const saida = addMinutes(entrada, rand(5, 120))

  const perm = permissoes.find(p =>
    p.usuario_id === user.id && p.setor_id === setor.id
  )

  let permitido = false

  if (perm && user.ativo) {
    if (entrada >= perm.horario_inicio && entrada <= perm.horario_fim) {
      permitido = true
    }
  }

  acessos.push({
    id: acessoId++,
    usuario_id: user.id,
    setor_id: setor.id,
    horario_entrada: entrada,
    horario_saida: saida,
    acesso_permitido: permitido
  })
}

// ================= CSV EXPORT =================
function toCSV(arr, headers) {
  const lines = [headers.join(",")]
  arr.forEach(obj => {
    lines.push(headers.map(h => obj[h]).join(","))
  })
  return lines.join("\n")
}

fs.writeFileSync(`${CSV_DIR}/usuarios.csv`,
  toCSV(usuarios, ["id","matricula","nome","role","ativo"])
)

fs.writeFileSync(`${CSV_DIR}/setores.csv`,
  toCSV(setores, ["id","nome"])
)

fs.writeFileSync(`${CSV_DIR}/permissoes.csv`,
  toCSV(permissoes, ["id","usuario_id","setor_id","horario_inicio","horario_fim","pode_entrar"])
)

fs.writeFileSync(`${CSV_DIR}/acessos.csv`,
  toCSV(acessos, ["id","usuario_id","setor_id","horario_entrada","horario_saida","acesso_permitido"])
)
console.log("CSVs gerados com sucesso!")
