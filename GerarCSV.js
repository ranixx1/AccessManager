import fs from "fs"
import path from "path"

// ================= SETUP =================
const CSV_DIR = path.resolve("csv")

if (!fs.existsSync(CSV_DIR)) {
  fs.mkdirSync(CSV_DIR)
}

// CONFIG
const QTD_USUARIOS = 10
const QTD_SETORES = 5
const QTD_ACESSOS = 10000

// ================= UTILS =================
function rand(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min
}

function timeToMinutes(t) {
  const [h, m] = t.split(":").map(Number)
  return h * 60 + m
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

function randomDateTime(daysBack = 30) {
  const now = new Date()
  const d = new Date(now)
  d.setDate(now.getDate() - rand(0, daysBack))
  d.setHours(rand(6, 22))
  d.setMinutes(rand(0, 59))
  d.setSeconds(0)
  d.setMilliseconds(0)
  return d.toISOString().slice(0, 19)
}

// ================= USERS =================
const roles = ["ADMIN", "MANAGER", "EMPLOYEE", "VISITOR"]

const usuarios = []
for (let i = 1; i <= QTD_USUARIOS; i++) {
  usuarios.push({
    id: i,
    matricula: rand(100000, 999999),
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
  if (u.role === "VISITOR") return
  if (Math.random() <= 0.3) return // 70% têm permissão

  const qtdPerm = rand(1, 2)

  for (let i = 0; i < qtdPerm; i++) {
    const setor = setores[rand(0, setores.length - 1)]

    if (permissoes.some(p => p.usuario_id === u.id && p.setor_id === setor.id)) {
      continue
    }

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

  const entradaDT = randomDateTime()
  const horaEntrada = entradaDT.slice(11, 16)

  const perm = permissoes.find(p =>
    p.usuario_id === user.id && p.setor_id === setor.id
  )

  let permitido = false

  if (perm && user.ativo) {
    const hEntrada = timeToMinutes(horaEntrada)
    if (
      hEntrada >= timeToMinutes(perm.horario_inicio) &&
      hEntrada <= timeToMinutes(perm.horario_fim)
    ) {
      permitido = true
    }
  }

  let saidaDT = ""

  if (permitido) {
    const saidaHora = addMinutes(horaEntrada, rand(5, 120))
    const d = new Date(entradaDT)
    const [h, m] = saidaHora.split(":").map(Number)
    d.setHours(h)
    d.setMinutes(m)
    saidaDT = d.toISOString().slice(0, 19)
  }

  acessos.push({
    id: acessoId++,
    usuario_id: user.id,
    setor_id: setor.id,
    horario_entrada: entradaDT,
    horario_saida: saidaDT,
    acesso_permitido: permitido
  })
}

// ================= CSV EXPORT =================
function toCSV(arr, headers) {
  const lines = [headers.join(",")]
  arr.forEach(obj => {
    lines.push(headers.map(h => obj[h] ?? "").join(","))
  })
  return lines.join("\n")
}

fs.writeFileSync(
  `${CSV_DIR}/usuarios.csv`,
  toCSV(usuarios, ["id", "matricula", "nome", "role", "ativo"])
)

fs.writeFileSync(
  `${CSV_DIR}/setores.csv`,
  toCSV(setores, ["id", "nome"])
)

fs.writeFileSync(
  `${CSV_DIR}/permissoes.csv`,
  toCSV(permissoes, ["id", "usuario_id", "setor_id", "horario_inicio", "horario_fim", "pode_entrar"])
)

fs.writeFileSync(
  `${CSV_DIR}/acessos.csv`,
  toCSV(acessos, ["id", "usuario_id", "setor_id", "horario_entrada", "horario_saida", "acesso_permitido"])
)

console.log("CSVs gerados com sucesso!")
