export function getToken(){
  return localStorage.getItem('token');
}

export function setToken(token){
  localStorage.setItem('token',token);
}

export function removeToken(){
  localStorage.removeItem('token');
}

export function getIdentity(){
  return localStorage.getItem('identity');
}

export function setIdentity(identity){
  localStorage.setItem('identity',identity);
}

export function removeIdentity(){
  localStorage.removeItem('identity');
}