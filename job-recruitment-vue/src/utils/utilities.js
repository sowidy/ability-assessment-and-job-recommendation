export function formatDate(date, format = true) {
  date = new Date(date);
  if (Number.isNaN(date.getTime())) {
    return false;
  }

  const Y = date.getFullYear();
  const M = (parseInt(date.getMonth()) + 1).toString().padStart(2, 0);
  const d = date
    .getDate()
    .toString()
    .padStart(2, 0);
  const h = date
    .getHours()
    .toString()
    .padStart(2, 0);
  const m = date
    .getMinutes()
    .toString()
    .padStart(2, 0);
  const s = date
    .getSeconds()
    .toString()
    .padStart(2, 0);

  return format ? `${Y}-${M}-${d} ${h}:${m}:${s}` : `${Y}-${M}-${d}`;
}