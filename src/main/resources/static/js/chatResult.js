let data = []

const refreshData = async () => {
    await fetch("/result")
        .then(async (res) => {
            data = await res.json()
            console.log(data)
            updateData()
        })
}

const updateData = () => {
    let container = document.getElementById('container')
    container.innerHTML = ''

    for (const item of data) {
        let div = document.createElement('a')
        div.setAttribute("data-modal-target", "defaultModal")
        div.setAttribute("data-modal-toggle","defaultModal")
        div.className = "p-2 flex flex-col border rounded-lg space-y-2 text-stone-600 cursor-pointer"
        div.addEventListener("click", () => {
            console.log(item)
            const modalCompany = document.getElementById("modal-company")
            const modalTitle = document.getElementById("modal-title")
            const modalUrl = document.getElementById("modal-url")
            const modalRegion = document.getElementById("modal-region")
            const modalCareer = document.getElementById("modal-career")
            const modalTime = document.getElementById("modal-time")
            const modalSalary = document.getElementById("modal-salary")
            const modalType = document.getElementById("modal-type")

            modalCompany.innerText = item.company
            modalTitle.innerText = item.title
            modalUrl.setAttribute("href", item.url)
            modalRegion.innerText = item.region
            modalCareer.innerText = item.career
            modalTime.innerText = item.time
            modalSalary.innerText = item.salary
            modalType.innerText = item.type
        })

        div.innerHTML = `
                <div class="text-xs truncate text-ellipsis">${item.company}</div>
                <div class="text-black font-bold truncate text-ellipsis">${item.title}</div>
                <div class="flex justify-between">
                  <div class="w-1/3 flex justify-start">
                    <img class="w-4 h-4" src="/icons/location.png" alt="loc">
                    <span class="w-full text-xs truncate text-ellipsis">${item.region}</span>
                  </div>
                  <div class="w-1/3 flex justify-start">
                    <img class="w-4 h-4" src="/icons/profile.png" alt="edu">
                    <span class="text-xs truncate text-ellipsis">${item.education}</span>
                  </div>
                  <div class="w-1/3 flex justify-start">
                    <img class="w-4 h-4" src="/icons/approval.png" alt="years">
                    <span class="text-xs truncate text-ellipsis">${item.career}</span>
                  </div>
                </div>
            `

        container.appendChild(div)
    }
}