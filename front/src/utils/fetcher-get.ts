export async function fetcherGet(url: string) {
  const rsp = await fetch(url);
  if (rsp.ok) {
    return await rsp.json();
  } else {
    throw new Error(rsp.statusText);
  }
}
